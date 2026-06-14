/**
 * @(#)Operation.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
 *                            <p> 
 *                                Redistribution and use in source and binary forms, with or without modification,
 *                                are permitted provided that the following conditions are met:
 *                            </p>
 *                            <ul>
 *                                <li>
 *                                    Redistribution of source code must retain the above copyright notice,
 *                                    this list of conditions and the following disclaimer.
 *                                </li>
 *                                <li>
 *                                    Redistribution in binary form must reproduce the above copyright notice,
 *                                    this list of conditions and the following disclaimer in the documentation
 *                                    and/or other materials provided with the distribution.
 *                                </li>
 *                                <li>
 *                                    Neither the name of all9s Solutions, nor the names of contributors
 *                                    may be used to endorse or promote products derived from this software
 *                                    without specific prior written permission.
 *                                </i>
 *                            </ul>
 *                            <p>
 *                                <b>Disclaimer:</b> This software is provided "AS IS," without a warranty of
 *                                any kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 *                                INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *                                PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. all9s Solutions,
 *                                AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS
 *                                A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *                                IN NO EVENT WILL all9s Solutions, OR ITS LICENSORS BE LIABLE FOR ANY
 *                                LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 *                                INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 *                                LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN
 *                                IF all9s Solutions, HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 *                                DAMAGES. You acknowledge that this software is not designed, licensed or
 *                                intended for use in the design, construction, operation or maintenance of any
 *                                nuclear facility.
 *                            </p>
 */
package com.all9ssolutions.lumberjack.business;

import static com.all9ssolutions.lumberjack.Activator.PLUGIN_ID;
import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.ACOM;
import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.LOG4J;
import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.UTIL;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.ui.CodeStyleConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

import com.all9ssolutions.lumberjack.Activator;
import com.all9ssolutions.lumberjack.Activator.MethodMap;
import com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator;
import com.all9ssolutions.lumberjack.generators.CommonsLoggingGenerator;
import com.all9ssolutions.lumberjack.generators.ExcludedNamesMap;
import com.all9ssolutions.lumberjack.generators.JavaUtilLoggingGenerator;
import com.all9ssolutions.lumberjack.generators.Log4jLoggingGenerator;
import com.all9ssolutions.lumberjack.preferences.PreferenceConstants;

/**
 * This class is used to do the work portion of generating logging statements
 * for selected methods for the Java class in the current editor.
 *
 */
public class Operation implements IWorkspaceRunnable, PreferenceConstants {
	private IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
	private static AbstractLoggingGenerator generator;
	private static ICompilationUnit iunit;
	private static CompilationUnit unit;
	private static ImportRewrite importRewrite;

	/**
	 * overloaded constructor instantiates this class
	 *
	 * @param type the instance of {@link org.eclipse.jdt.core.IType} for the Java
	 *             class in the current editor
	 * @throws CoreException exception if the compilation unit cannot be parsed
	 */
	public Operation(IType type) throws CoreException {
		super();
		iunit = type.getCompilationUnit();
		try {
			unit = new Parser().parse(type.getCompilationUnit());
		} catch (MalformedTreeException e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} // end try/catch
		importRewrite = CodeStyleConfiguration.createImportRewrite(unit, true);
		if (unit.getAST().hasResolvedBindings()) {
			importRewrite.setUseContextToFilterImplicitImports(true);
		} // end if
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask("Generating logging statements for methods...", 1);
			generator = LoggingGeneratorFactory.getLoggingGenerator(preferences.getString(FRAMEWORK_COMBO));
			if (null == generator) {
				throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID,
						Activator.getDefault().getResourceBundle().getString("preferences.error.framework"), null));
			} // end if
			((AbstractLoggingGenerator) generator).clearEdits();
			String qualifiedClassPath = Activator.getDefault().getQualifiedClassPath();
			MethodMap methodMap = Activator.getDefault().getMethodMap();
			for (TypeDeclaration type : methodMap.keySet()) {
				String className = qualifiedClassPath + getFullyQualifiedName(type);
				((AbstractLoggingGenerator) generator).createLoggerVariable(unit.getAST(), type, className);
				ExcludedNamesMap excluded = new ExcludedNamesMap();
				excluded.loadExcludedClassNames(type);
				List<MethodDeclaration> methods = methodMap.get(type);
				for (int i = 0, j = methods.size(); i < j; i++) {
					MethodDeclaration method = methods.get(i);
					if (method.getBody() == null) {
						continue;
					} // end if
					// Load a map of names to be excluded when generating variable names.
					excluded.loadExcludedLocalNames(method.parameters(), method.getBody().statements());
					// Add logging statements and logic to the generated instance and replace it in
					// the list rewriter.
					((AbstractLoggingGenerator) generator).setExcludedNames(excluded);
					((AbstractLoggingGenerator) generator).generate(method);
				} // end for
			} // end for
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} finally {
			monitor.done();
		} // end try/catch/finally
	}// end run

	/**
	 * gets the fully qualified class name separated with a dot
	 * <p>
	 * if the parameter {@link org.eclipse.jdt.core.dom.TypeDeclaration} is a nested
	 * class, then this method will run recursively to identify the parent instances
	 *
	 * @param type the current {@link org.eclipse.jdt.core.dom.TypeDeclaration}
	 * @return the qualified class path of the current type
	 */
	private String getFullyQualifiedName(TypeDeclaration type) {
		StringBuilder className = new StringBuilder();
		if (type.getParent().getNodeType() == TYPE_DECLARATION) {
			className.append(getFullyQualifiedName((TypeDeclaration) type.getParent()));
		} // end if
		className.append('.').append(type.getName().getFullyQualifiedName());
		return className.toString();
	}// end getFullyQualifiedName

	/**
	 * compiles all of the text edit changes in the same compilation unit
	 * 
	 * @return an instance of
	 *         {@link org.eclipse.jdt.core.refactoring.CompilationUnitChange}
	 * @throws CoreException exception if the text edits cannot be compiled
	 */
	public static CompilationUnitChange createChange() throws CoreException {
		CompilationUnitChange change = new CompilationUnitChange(iunit.getElementName(), iunit);
		change.setKeepPreviewEdits(true);
		change.setEdit(new MultiTextEdit());
		TextEdit importsEdit = getImportRewrite().rewriteImports(null);
		if (!(isEmptyEdit(importsEdit))) {
			change.getEdit().addChild(importsEdit);
			change.addTextEditGroup(new TextEditGroup("Update imports", importsEdit));
		} // end if
		List<TextEdit> edits = ((AbstractLoggingGenerator) generator).getEdits();
		for (int i = 0, j = edits.size(); i < j; i++) {
			if (!(isEmptyEdit(edits.get(i)))) {
				change.getEdit().addChild(edits.get(i));
				change.addTextEditGroup(new TextEditGroup("Update Method", edits.get(i)));
			} // end if
		} // end for
		return (isEmptyEdit(change.getEdit())) ? null : change;
	}// end createChange

	/**
	 * tests if the parameter edit is an instance of
	 * {@link org.eclipse.text.edits.MultiTextEdit} and has children edits
	 * 
	 * @param edit the {@link org.eclipse.text.edits.TextEdit} with code changes to
	 *             be added to the whole
	 * @return {@code true} if the parameter passes the conditional
	 */
	private static boolean isEmptyEdit(TextEdit edit) {
		return ((edit.getClass() == MultiTextEdit.class) && (!(edit.hasChildren())));
	}// end isEmptyEdit

	/**
	 * returns the import rewrite object
	 * 
	 * @return the single instance of the
	 *         {@link org.eclipse.jdt.core.dom.rewrite.ImportRewrite} for the
	 *         current operation
	 */
	public static ImportRewrite getImportRewrite() {
		return importRewrite;
	}// end getImportRewrite

	/**
	 * returns the compilation unit being operated on
	 * 
	 * @return the single instance of the
	 *         {@link org.eclipse.jdt.core.dom.CompilationUnit} for the current
	 *         operation
	 */
	public CompilationUnit getUnit() {
		return unit;
	}// end getUnit

	/**
	 * This nested class is used to model an instance of an
	 * {@link org.eclipse.jdt.core.dom.ASTParser} customized for the Ender plug-in
	 * application.
	 *
	 */
	private class Parser {
		private ASTParser parser;

		/**
		 * default constructor instantiates this class setting an instance of
		 * {@link org.eclipse.jdt.core.dom.ASTParser} with the parser level set to
		 * compilation unit
		 */
		public Parser() {
			super();
			parser = ASTParser.newParser(ASTParser.K_COMPILATION_UNIT);
		}// end constructor

		/**
		 * parses the parameter instance of type root
		 * 
		 * @param typeRoot the {@link org.eclipse.jdt.core.ITypeRoot} to be parsed
		 * @return an instance of {@link org.eclipse.jdt.core.dom.CompilationUnit}
		 */
		public CompilationUnit parse(ITypeRoot typeRoot) {
			parser.setSource(typeRoot);
			parser.setResolveBindings(true);
			parser.setStatementsRecovery(true);
			parser.setBindingsRecovery(true);
			IJavaProject project = ((IJavaElement) typeRoot).getJavaProject();
			Map<String, String> options = project.getOptions(true);
			for (Iterator<String> iter = options.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				String value = options.get(key);
				if ((!("error".equals(value))) && (!("warning".equals(value)))) {
					continue;
				} // end if
				options.put(key, "ignore");
			} // end for
			options.put("org.eclipse.jdt.core.compiler.maxProblemPerUnit", "0");
			options.put("org.eclipse.jdt.core.compiler.taskTags", "");
			parser.setCompilerOptions(options);
			return (CompilationUnit) parser.createAST(null);
		}// end parse
	}// end nested class

	/**
	 * This nested class retrieves a sub-class of
	 * {@link com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator}
	 * class based on the client selected logger framework.
	 * 
	 */
	public static class LoggingGeneratorFactory {

		/**
		 * {@code private} constructor blocks instantiation of this class
		 * <p>
		 * all methods in this class should be {@code static}
		 * </p>
		 */
		private LoggingGeneratorFactory() {
			super();
		}// end constructor

		/**
		 * returns an instance of the {@link AbstractLoggingGenerator} class based on
		 * the incoming parameter
		 *
		 * @param type the {@code String} value of the type of generator to get
		 * @return the implementation of the
		 *         {@link com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator}
		 *         class matching the type
		 */
		public static AbstractLoggingGenerator getLoggingGenerator(String type) {
			AbstractLoggingGenerator generator = null;
			switch (type) {
			case LOG4J:
				generator = new Log4jLoggingGenerator();
				break;
			case ACOM:
				generator = new CommonsLoggingGenerator();
				break;
			case UTIL:
				generator = new JavaUtilLoggingGenerator();
				break;
			}
			return generator;
		} // end getLoggingGenerator
	}// end nested class
}// end class
