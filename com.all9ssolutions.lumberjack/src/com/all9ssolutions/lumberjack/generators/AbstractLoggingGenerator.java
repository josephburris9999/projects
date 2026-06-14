/**
 * @(#)AbstractLoggingGenerator.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.lumberjack.generators;

import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_comment;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_no_javadoc;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_break;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_continue;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_synchronized;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_variable;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_if;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_else;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_do;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_while;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_enhanced_for;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_for;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_switch;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_logging_msg_method;
import static org.eclipse.jdt.core.dom.ASTNode.ANONYMOUS_CLASS_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.ASSIGNMENT;
import static org.eclipse.jdt.core.dom.ASTNode.BLOCK;
import static org.eclipse.jdt.core.dom.ASTNode.BREAK_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.CATCH_CLAUSE;
import static org.eclipse.jdt.core.dom.ASTNode.CLASS_INSTANCE_CREATION;
import static org.eclipse.jdt.core.dom.ASTNode.CONTINUE_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.EMPTY_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.EXPRESSION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.IF_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.LABELED_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.NULL_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.RETURN_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.SIMPLE_NAME;
import static org.eclipse.jdt.core.dom.ASTNode.SUPER_METHOD_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.SWITCH_CASE;
import static org.eclipse.jdt.core.dom.ASTNode.SWITCH_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.SYNCHRONIZED_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TRY_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.TextEdit;

import com.all9ssolutions.lumberjack.Activator;
import com.all9ssolutions.lumberjack.business.Operation;
import com.all9ssolutions.lumberjack.preferences.PreferenceConstants;

/**
 * This class is the super class for the generators. It provides the bulk of the
 * logic for generating logging statements for the Java class in the current
 * editor.
 * <p>
 * Generated logging will be in the following default format:
 *
 * <pre>
 *
 * logger.entering(methodName);
 * if (logger.isTraceEnabled()) {
 * 	logger.tracep(methodName,
 * 			&quot;This Method gets Batch Interfaces, with an optional tranType filter. The entry parameters are tranTypes: {}, systemVendorCds: {}&quot;,
 * 			new Object[] { String.valueOf(tranTypes), String.valueOf(systemVendorCds) });
 * } // end if
 * List&lt;BatchInterfaceDO&gt; activeInterfaceDOList = batchDAO.getActiveInterfaces(tranTypes, systemVendorCds);
 * List&lt;BatchInterfaceDTO&gt; returnList = batchAssembler.toBatchInterfaceList(activeInterfaceDOList);
 * if (logger.isTraceEnabled()) {
 * 	logger.tracep(methodName, &quot;Returning BatchInterfaceDTOs : {}&quot;, new Object[] { String.valueOf(returnList) });
 * } // end if
 * logger.exiting(methodName);
 * </pre>
 *
 */
public abstract class AbstractLoggingGenerator implements PreferenceConstants {
	/** unique identifier for the java.util.logging framework */
	public static final String UTIL = "util";
	/** unique identifier for the Log4j logging framework */
	public static final String LOG4J = "log4j";
	/** unique identifier for the Apache Commons logging framework */
	public static final String ACOM = "commons-logging";
	/**
	 * a {@code String[][]} of values to be displayed in the logging frameworks drop
	 * box
	 * <p>
	 * each entry contains the display label followed by the backing value used in
	 * the generator logic
	 * </p>
	 */
	public static final String[][] LOGGING_FRAMEWORKS = { //
			{ "java.util.logging", UTIL }, //
			{ "Log4j Logging", LOG4J }, //
			{ "Apache Commons Logging", ACOM } //
	};

	/** the default value for all logging frameworks */
	public static final String OFF = "OFF";
	protected IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
	protected static final String ARRAYS = "java.util.Arrays";
	protected static final String BOOLEAN = "boolean";
	protected static final String BYTE = "byte";
	protected static final String CHAR = "char";
	protected static final String COMMA_SPACE = ", ";
	protected static final String DEFAULT = "default";
	protected static final String DOUBLE = "double";
	protected static final String EMPTY = "";
	protected static final String EQUALS = "=";
	protected static final String FLOAT = "float";
	protected static final String INT = "int";
	protected static final String LONG = "long";
	protected static final String SHORT = "short";
	protected static final String STRING = "String";
	protected static final String TO_STRING = "toString";
	protected static final String VALUE_OF = "valueOf";
	protected static final String VOID = "void";
	private ExcludedNamesMap excluded;
	protected List<TextEdit> edits;

	/**
	 * {@code protected} constructor blocks instantiation of this class to
	 * sub-classes only
	 */
	protected AbstractLoggingGenerator() {
		super();
		edits = new ArrayList<TextEdit>();
	}// end constructor

	/**
	 * returns the qualified class name used as the data type for a logger variable
	 * declaration
	 * <p>
	 * this value must be provided by a sub-class for the logging framework
	 * </p>
	 *
	 * @return the qualified class name of a logger class
	 */
	public abstract String getLoggingDataTypeClassName();

	/**
	 * returns the qualified class name used as the implementation class for a
	 * logger variable declaration
	 * <p>
	 * this value may be the same as the {@link #getLoggingDataTypeClassName()}
	 * value, however in the case that it is not this method is provided
	 * </p>
	 *
	 * @return the qualified class name of a logger implementation class
	 */
	public String getLoggingImplementationClassName() {
		return getLoggingDataTypeClassName();
	}// end getLoggingImplementationClassName

	/**
	 * returns the available logging levels for the logging framework
	 * <p>
	 * this value must be provided by a sub-class for the logging framework
	 * </p>
	 * <p>
	 * <b>the {@code OFF} logging level should be included in the array of levels
	 * </b>
	 * </p>
	 * 
	 * @return a {@code String[][]} of available logging levels for the logging
	 *         framework
	 */
	public abstract String[][] getLoggingLevels();

	/**
	 * returns the logger instantiation method call
	 * <p>
	 * this value must be provided by a sub-class for the logging framework
	 * </p>
	 *
	 * @return the {@code String} value of the logger instantiation method
	 */
	public abstract String getLoggingInstantiationMethod();

	/**
	 * returns the simple start of the method entering log statement
	 * <p>
	 * this value may be overridden by a sub-class
	 * </p>
	 * 
	 * @return the {@code String} to begin a method entering log statement
	 */
	protected String getEnteringLogStatement() {
		return "entering ";
	}// end getEnteringLogStatement

	/**
	 * returns the simple phrase leading the entry parameters log statement
	 * <p>
	 * this value may be overridden by a sub-class
	 * </p>
	 * 
	 * @return the {@code String} to begin the entry parameters log statement
	 */
	protected String getEntryParametersLogStatement() {
		return "entry parameters: ";
	}// end getEntryParametersLogStatement

	/**
	 * returns the simple phrase leading the return value log statement
	 * <p>
	 * this value may be overridden by a sub-class
	 * </p>
	 * 
	 * @return the {@code String} to begin the return value log statement
	 */
	protected String getReturnValueLogStatement() {
		return "return value: ";
	}// end getReturnValueLogStatement

	/**
	 * returns the simple start of the method exiting log statement
	 * <p>
	 * this value may be overridden by a sub-class
	 * </p>
	 * 
	 * @return the {@code String} to begin a method exiting log statement
	 */
	protected String getExitingLogStatement() {
		return "exiting ";
	}// end getExitingLogStatement

	/**
	 * returns the {@code is*Loggable} method for the parameter level associated
	 * with a logging framework
	 * <p>
	 * this value must be provided by a sub-class for the logging framework
	 * </p>
	 * 
	 * @return the {@code String} value of the {@code is*Loggable} method for a
	 *         specific logging level
	 */
	protected abstract String getIsLoggableMethod(String level);

	/**
	 * generates a logger variable based on client selections
	 *
	 * @param ast  the enclosing {@link org.eclipse.jdt.core.dom.AST} node to add to
	 * @param type the {@link org.eclipse.jdt.core.dom.TypeDeclaration} to examine
	 * @param name the fully qualified name of the class to be used as the argument
	 *             in the logger instantiation
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	public void createLoggerVariable(AST ast, TypeDeclaration type, String name) throws JavaModelException {
		// Add the declaring class type as an import.
		String declaringClassName = Operation.getImportRewrite().addImport(getLoggingDataTypeClassName());
		String implementationClassName = (!declaringClassName.equals(getLoggingImplementationClassName()))
				? Operation.getImportRewrite().addImport(getLoggingImplementationClassName())
				: declaringClassName;
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName(preferences.getString(LOGGER_VAR_NAME)));
		StringLiteral literal = ast.newStringLiteral();
		literal.setLiteralValue(name);
		MethodInvocation invocation = ast.newMethodInvocation();
		invocation.setExpression(ast.newName(implementationClassName));
		invocation.setName(ast.newSimpleName(getLoggingInstantiationMethod()));
		invocation.arguments().add(literal);
		fragment.setInitializer(invocation);
		// Create a variable declaration statement to hold the fragment.
		VariableDeclarationStatement variable = ast.newVariableDeclarationStatement(fragment);
		variable.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));
		if (hasStaticMethod(type)) {
			variable.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
		} // end if
		variable.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.FINAL_KEYWORD));
		variable.setType(ast.newSimpleType(ast.newName(declaringClassName)));
		ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(type, type.getBodyDeclarationsProperty());
		rewrite.insertAt(variable, 0, null);
		edits.add(rewrite.getASTRewrite().rewriteAST());
	}// end createLoggerVariable

	/**
	 * tests if any of the methods, which logging will be generated for, are static
	 *
	 * @param type the {@link org.eclipse.jdt.core.dom.TypeDeclaration} to examine
	 * @return {@code true} if a method binding in the array is found to be static
	 */
	private boolean hasStaticMethod(TypeDeclaration type) {
		List<MethodDeclaration> methods = Activator.getDefault().getMethodMap().get(type);
		for (int i = 0, j = methods.size(); i < j; i++) {
			if (Modifier.isStatic(methods.get(i).getModifiers())) {
				return true;
			} // end if
		} // end for
		return false;
	}// end hasStaticMethods

	/**
	 * generates logging statements based on client selections from the wizard.
	 * <p>
	 * <b> this method must be overridden in a sub-class if special functionality is
	 * required by a logging framework </b>
	 * </p>
	 * 
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to add
	 *               logging statements to
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	public void generate(MethodDeclaration method) throws JavaModelException {
		ListRewrite rewrite = ASTRewrite.create(method.getAST()).getListRewrite(method.getBody(),
				Block.STATEMENTS_PROPERTY);
		doWriteEnter(method, rewrite);
		doBody(method, rewrite);
		if (!method.getBody().statements().isEmpty()) {
			// Test if the last statement is a return - even if the method return type is
			// void - and skip adding the exiting logging if it is.
			Statement statement = (Statement) method.getBody().statements()
					.get(method.getBody().statements().size() - 1);
			if (isVoidReturn(method) && statement.getNodeType() != RETURN_STATEMENT) {
				rewrite = ASTRewrite.create(method.getAST()).getListRewrite(method.getBody(),
						Block.STATEMENTS_PROPERTY);
				doWriteExit(method, rewrite, null);
			} // end if
		} // end if
	}// end generate

	/**
	 * clears the list of {@link org.eclipse.text.edits.TextEdit}s in preparation
	 * for logging statement generation
	 */
	public void clearEdits() {
		edits.clear();
	}// end clearEdits

	/**
	 * returns the list of {@link org.eclipse.text.edits.TextEdit}s performed by the
	 * logic
	 * 
	 * @return the list of {@code TextEdit} performed on the current class
	 */
	public List<TextEdit> getEdits() {
		return edits;
	}// end getEdits

	/**
	 * writes the method entering logging statement and adds it to a
	 * {@link java.util.List} of {@link org.eclipse.jdt.core.dom.Statement}s for
	 * {@code return}
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method  the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *                generate logging for
	 * @param rewrite the {@link org.eclipse.jdt.core.dom.rewrite.ListRewrite}
	 *                accepting changes for the current method
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doWriteEnter(MethodDeclaration method, ListRewrite rewrite) throws JavaModelException {
		if (!OFF.equals(preferences.getString(ENTER_LEVEL))) {
			AST ast = method.getAST();
			StringLiteral literal = createStringLiteral(ast,
					getEnteringLogStatement() + method.getName().getFullyQualifiedName());
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME),
					preferences.getString(ENTER_LEVEL).toLowerCase(), literal);
			ExpressionStatement statement = ast.newExpressionStatement(invocation);
			rewrite.insertFirst(statement, null);
			IfStatement ifstatement = doWriteEnterIf(method);
			Block block = null;
			Statement comment = doWriteComment(method, rewrite);
			if (null != comment) {
				if (preferences.getBoolean(WRITE_ENTER_IF)) {
					block = ast.newBlock();
					block.statements().add(comment);
				} else {
					rewrite.insertAfter(comment, statement, null);
				} // end if/else
			} // end if
			Statement params = doWriteParams(method);
			if (null != params) {
				if (preferences.getBoolean(WRITE_ENTER_IF)) {
					if (block == null) {
						block = ast.newBlock();
					} // end if
					block.statements().add(params);
				} else {
					if (null == comment) {
						rewrite.insertAfter(params, statement, null);
					} else {
						rewrite.insertAfter(params, comment, null);
					} // end if/else
				} // end if/else
			} // end if
			if (null != ifstatement && null != block) {
				ifstatement.setThenStatement(block);
				rewrite.insertAfter(ifstatement, statement, null);
			} // end if
			edits.add(rewrite.getASTRewrite().rewriteAST());
		} // end if
	}// end doWriteEnter

	/**
	 * writes the method entering {@code if} logging statement to contain the
	 * include parameters and/or Javadoc comment logging statements
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @return an instance of {@link org.eclipse.jdt.core.dom.IfStatement}
	 */
	protected IfStatement doWriteEnterIf(MethodDeclaration method) {
		IfStatement statement = null;
		if (preferences.getBoolean(WRITE_ENTER_IF)
				&& (preferences.getBoolean(WRITE_COMMENT) || preferences.getBoolean(INCLUDE_PARAMS))) {
			AST ast = method.getAST();
			String isMethod = getIsLoggableMethod(preferences.getString(ENTER_LEVEL));
			statement = ast.newIfStatement();
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), isMethod,
					null);
			statement.setExpression(invocation);
		} // end if
		return statement;
	}// end doWriteEnterIf

	/**
	 * writes the Javadoc comment associated with the method in a logging statement
	 * <p>
	 * if the method does not have a Javadoc, then the logging line will be replaced
	 * with a comment stating NO JAVADOC COMMENT AVAILABLE
	 * </p>
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method  the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *                generate logging for
	 * @param rewrite the {@link org.eclipse.jdt.core.dom.rewrite.ListRewrite}
	 *                accepting changes for the current method
	 * @return a logging {@code org.eclipse.jdt.core.dom.Statement} with the method
	 *         comment
	 */
	protected Statement doWriteComment(MethodDeclaration method, ListRewrite rewrite) {
		Statement statement = null;
		if (preferences.getBoolean(WRITE_COMMENT)) {
			AST ast = method.getAST();
			String literalValue = getComment(method);
			if (EMPTY.equals(literalValue)) {
				statement = (Statement) rewrite.getASTRewrite()
						.createStringPlaceholder(LUMBERJACK_logging_msg_no_javadoc, EMPTY_STATEMENT);
			} else {
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME),
						preferences.getString(ENTER_LEVEL).toLowerCase(), createStringLiteral(ast, literalValue));
				statement = ast.newExpressionStatement(invocation);
			} // end if/else
		} // end if
		return statement;
	}// end doWriteComment

	/**
	 * retrieves the {@code String} content of the
	 * {@link org.eclipse.jdt.core.dom.Javadoc} associated with the parameter method
	 * <p>
	 * the targeted content is the first line of a Javadoc
	 * </p>
	 * <p>
	 * if a Javadoc tag such as {@code @param} is found to start the first comment,
	 * then it will be assumed that no description comment is available
	 * </p>
	 * 
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @return the cleaned and parsed value of the Javadoc
	 */
	private String getComment(MethodDeclaration method) {
		String comment = "";
		Javadoc javadoc = method.getJavadoc();
		if (null != javadoc) {
			if (!javadoc.tags().isEmpty()) {
				// Replace the leading asterisk.
				comment = String.valueOf(javadoc.tags().get(0));
				if (comment.contains("*")) {
					comment = comment.substring(comment.indexOf("*") + 1);
				} // end if
				comment = (JavadocParser.is(comment.trim())) ? JavadocParser.clean(comment.trim()) : "";
			} // end if
		} // end if
		return comment;
	}// end getComment

	/**
	 * writes the parameter arguments for the method
	 * <p>
	 * if no parameters are present, then {@code null} is returned
	 * </p>
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @return an instance of {@link org.eclipse.jdt.core.dom.ExpressionStatement}
	 */
	@SuppressWarnings("unchecked")
	protected ExpressionStatement doWriteParams(MethodDeclaration method) {
		ExpressionStatement statement = null;
		if (preferences.getBoolean(INCLUDE_PARAMS) && !method.parameters().isEmpty()) {
			AST ast = method.getAST();
			MethodInvocation invocation = createMethodInvocation(method.getAST(),
					preferences.getString(LOGGER_VAR_NAME), preferences.getString(ENTER_LEVEL).toLowerCase(), null);
			List<SingleVariableDeclaration> parameters = method.parameters();
			SingleVariableDeclaration variable = parameters.get(0);
			InfixExpression expression = ast.newInfixExpression();
			expression.setLeftOperand(createStringLiteral(ast,
					getEntryParametersLogStatement() + variable.getName().toString() + EQUALS));
			expression.setOperator(InfixExpression.Operator.PLUS);
			if (variable.getType().isArrayType()) {
				expression.setRightOperand(createMethodInvocation(ast, Operation.getImportRewrite().addImport(ARRAYS),
						TO_STRING, ast.newSimpleName(variable.getName().toString())));
			} else {
				expression.setRightOperand(createMethodInvocation(ast, STRING, VALUE_OF,
						ast.newSimpleName(variable.getName().toString())));
			} // end if/else
			InfixExpression left, right;
			for (int i = 1, j = parameters.size(); i < j; i++) {
				variable = parameters.get(i);
				left = ast.newInfixExpression();
				left.setLeftOperand(expression);
				left.setOperator(InfixExpression.Operator.PLUS);
				left.setRightOperand(createStringLiteral(ast, COMMA_SPACE + variable.getName().toString() + EQUALS));
				right = ast.newInfixExpression();
				right.setLeftOperand(left);
				right.setOperator(InfixExpression.Operator.PLUS);
				right.setRightOperand(createMethodInvocation(ast, STRING, VALUE_OF,
						ast.newSimpleName(variable.getName().toString())));
				if (variable.getType().isArrayType()) {
					right.setRightOperand(createMethodInvocation(ast, Operation.getImportRewrite().addImport(ARRAYS),
							TO_STRING, ast.newSimpleName(variable.getName().toString())));
				} else {
					right.setRightOperand(createMethodInvocation(ast, STRING, VALUE_OF,
							ast.newSimpleName(variable.getName().toString())));
				} // end if/else
				expression = right;
			} // end for
			invocation.arguments().add(expression);
			statement = ast.newExpressionStatement(invocation);
		} // end if
		return statement;
	}// end doWriteParams

	/**
	 * this method is called recursively to process control statements found in the
	 * method
	 * <p>
	 * <b>While this method may be overridden in sub-classes to provide different
	 * functionality, it is NOT recommended!</b>
	 * </p>
	 * 
	 * @param method  the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *                generate logging for
	 * @param rewrite the {@link org.eclipse.jdt.core.dom.rewrite.ListRewrite}
	 *                accepting changes for the current method
	 * 
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doBody(MethodDeclaration method, ListRewrite rewrite) throws JavaModelException {
		int index = 0;
		ASTNode node;
		while (index < rewrite.getRewrittenList().size()) {
			node = (ASTNode) rewrite.getRewrittenList().get(index);
			// This test keeps generated values from being examined and logged.
			if (null != node.getParent()) {
				switch (node.getNodeType()) {
				case ANONYMOUS_CLASS_DECLARATION:
					doAnonymousClassDeclaration(method, (AnonymousClassDeclaration) node);
					break;
				case BREAK_STATEMENT:
					doBreakStatement(method, (BreakStatement) node);
					break;
				case CATCH_CLAUSE:
					doCatchClause(method, (CatchClause) node);
					break;
				case CONTINUE_STATEMENT:
					doContinueStatement(method, (ContinueStatement) node);
					break;
				case DO_STATEMENT:
					doDoStatement(method, (DoStatement) node);
					break;
				case ENHANCED_FOR_STATEMENT:
					doEnhancedForStatement(method, (EnhancedForStatement) node);
					break;
				case EXPRESSION_STATEMENT:
					doExpressionStatement(method, (ExpressionStatement) node);
					break;
				case FOR_STATEMENT:
					doForStatement(method, (ForStatement) node);
					break;
				case IF_STATEMENT:
					doIfStatement(method, (IfStatement) node);
					break;
				case LABELED_STATEMENT:
					doLabeledStatement(method, (LabeledStatement) node);
					break;
				case METHOD_DECLARATION:
					doMethodDeclaration(method, (MethodDeclaration) node);
					break;
				case RETURN_STATEMENT:
					doReturnStatement(method, (ReturnStatement) node);
					break;
				case SWITCH_STATEMENT:
					doSwitchStatement(method, (SwitchStatement) node);
					break;
				case SYNCHRONIZED_STATEMENT:
					doSynchronizedStatement(method, (SynchronizedStatement) node);
					break;
				case TRY_STATEMENT:
					doTryStatement(method, (TryStatement) node);
					break;
				case VARIABLE_DECLARATION_STATEMENT:
					doVariableDeclarationStatement(method, (VariableDeclarationStatement) node);
					break;
				case WHILE_STATEMENT:
					doWhileStatement(method, (WhileStatement) node);
					break;
				default:
					break;
				}// end switch
			} // end if
				// Increment outside of the switch to see the next statement of the iteration.
			index++;
		} // end while
	}// end doBody

	/**
	 * examines and logs control structures found within an anonymous class
	 * declaration
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * <p>
	 * <b> intentionally avoids stepping into anonymous class declarations</b>
	 * </p>
	 * 
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.AnonymousClassDeclaration}
	 *               to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doAnonymousClassDeclaration(MethodDeclaration method, AnonymousClassDeclaration node)
			throws JavaModelException {
		ExcludedNamesMap excludedhold = excluded.clone();
		excluded.loadExcludedLocalNames(method.parameters(), method.getBody().statements());
		ListRewrite rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node, Block.STATEMENTS_PROPERTY);
		doBody(method, rewrite);
		setExcludedNames(excludedhold);
	}// end doAnonymousClassDeclaration

	/**
	 * generates a logging statement for a {@code break} statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.BreakStatement} to examine
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doBreakStatement(MethodDeclaration method, BreakStatement node) throws JavaModelException {
		AST ast = node.getAST();
		Block block = findParentBlock(node);
		if (null != block) {
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(block.getParent().getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast, LUMBERJACK_logging_msg_break);
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertBefore(ast.newExpressionStatement(invocation), node, null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
		} // end if
	}// end doBreakStatement

	/**
	 * examines and logs control structures found within a {@code catch} block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.AnonymousClassDeclaration}
	 *               to examine
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doCatchClause(MethodDeclaration method, CatchClause node) throws JavaModelException {
		ListRewrite rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node.getBody(),
				Block.STATEMENTS_PROPERTY);
		doBody(method, rewrite);
	}// end doCatchClause

	/**
	 * generates a logging statement for a {@code continue} statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.ContinueStatement} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doContinueStatement(MethodDeclaration method, ContinueStatement node) throws JavaModelException {
		AST ast = node.getAST();
		Block block = findParentBlock(node);
		if (null != block) {
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(block.getParent().getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast, LUMBERJACK_logging_msg_continue);
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertBefore(ast.newExpressionStatement(invocation), node, null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
		} // end if
	}// end doContinueStatement

	/**
	 * examines and logs control structures found within a {@code do} loop block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.DoStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doDoStatement(MethodDeclaration method, DoStatement node) throws JavaModelException {
		AST ast = node.getAST();
		if (node.getBody().getNodeType() == BLOCK) {
			Block block = (Block) node.getBody();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_do, node.getExpression()));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
		} // end if
	}// end doDoStatement

	/**
	 * examines and logs control structures found within an enhanced {@code for}
	 * loop block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.EnhancedForStatement} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doEnhancedForStatement(MethodDeclaration method, EnhancedForStatement node)
			throws JavaModelException {
		AST ast = node.getAST();
		if (node.getBody().getNodeType() == BLOCK) {
			Block block = (Block) node.getBody();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast, MessageFormat.format(
						LUMBERJACK_logging_msg_enhanced_for, node.getParameter() + " : " + node.getExpression()));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
		} // end if
	}// end doEnhancedForStatement

	/**
	 * redirects logic for various {@code ExpressionStatement}s
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.ExpressionStatement} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doExpressionStatement(MethodDeclaration method, ExpressionStatement node) throws JavaModelException {
		Expression expression = node.getExpression();
		if (null != expression) {
			if (expression.getNodeType() == ASSIGNMENT) {
				expression = ((Assignment) expression).getRightHandSide();
				if (expression.getNodeType() == CLASS_INSTANCE_CREATION) {
					AnonymousClassDeclaration declaration = ((ClassInstanceCreation) expression)
							.getAnonymousClassDeclaration();
					if (null != declaration) {
						ListRewrite rewrite = ASTRewrite.create(declaration.getAST()).getListRewrite(declaration,
								Block.STATEMENTS_PROPERTY);
						doBody(method, rewrite);
					} // end if
				} // end if
			} else {
				switch (expression.getNodeType()) {
				case METHOD_INVOCATION:
					doMethodInvocation(method, (MethodInvocation) node.getExpression());
					break;
				case SUPER_METHOD_INVOCATION:
					doSuperMethodInvocation(method, (SuperMethodInvocation) node.getExpression());
					break;
				}// end switch
			} // end if/else
		} // end if
	}// end doExpressionStatement

	/**
	 * examines and logs control structures found within a {@code for} loop block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.ForStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doForStatement(MethodDeclaration method, ForStatement node) throws JavaModelException {
		AST ast = node.getAST();
		if (node.getBody().getNodeType() == BLOCK) {
			Block block = (Block) node.getBody();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_for, node.getExpression()));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
		} // end if
	}// end doForStatement

	/**
	 * examines and logs control structures found within an {@code if} block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.IfStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doIfStatement(MethodDeclaration method, IfStatement node) throws JavaModelException {
		AST ast = node.getAST();
		if (node.getThenStatement().getNodeType() == BLOCK) {
			Block block = (Block) node.getThenStatement();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_if, node.getExpression()));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
			// Call this method recursively to handle if/else if.. statements.
			if (null != node.getElseStatement()) {
				if (node.getElseStatement().getNodeType() == IF_STATEMENT) {
					doIfStatement(method, (IfStatement) node.getElseStatement());
				} else {
					ast = node.getAST();
					if (node.getElseStatement().getNodeType() == BLOCK) {
						block = (Block) node.getElseStatement();
						rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
						if (!OFF.equalsIgnoreCase(level)) {
							StringLiteral literal = createStringLiteral(ast, LUMBERJACK_logging_msg_else);
							MethodInvocation invocation = createMethodInvocation(ast,
									preferences.getString(LOGGER_VAR_NAME), level, literal);
							rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
							edits.add(rewrite.getASTRewrite().rewriteAST());
						} // end if
						doBody(method, rewrite);
					} // end if
				} // end if/else
			} // end if
		} // end if
	}// end doIfStatement

	/**
	 * redirects logic for various {@code LabeledStatement}s
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.LabeledStatement} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doLabeledStatement(MethodDeclaration method, LabeledStatement node) throws JavaModelException {
		if (null != node.getBody()) {
			ListRewrite rewrite;
			switch (node.getBody().getNodeType()) {
			case BLOCK:
				rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node.getBody(), Block.STATEMENTS_PROPERTY);
				doBody(method, rewrite);
				break;
			case DO_STATEMENT:
				doDoStatement(method, (DoStatement) node.getBody());
				break;
			case ENHANCED_FOR_STATEMENT:
				doEnhancedForStatement(method, (EnhancedForStatement) node.getBody());
				break;
			case FOR_STATEMENT:
				doForStatement(method, (ForStatement) node.getBody());
				break;
			case IF_STATEMENT:
				doIfStatement(method, (IfStatement) node.getBody());
				break;
			case LABELED_STATEMENT:
				doLabeledStatement(method, (LabeledStatement) node.getBody());
				break;
			case SWITCH_STATEMENT:
				doSwitchStatement(method, (SwitchStatement) node.getBody());
				break;
			case SYNCHRONIZED_STATEMENT:
				doSynchronizedStatement(method, (SynchronizedStatement) node.getBody());
				break;
			case TRY_STATEMENT:
				doTryStatement(method, (TryStatement) node.getBody());
				break;
			case WHILE_STATEMENT:
				doWhileStatement(method, (WhileStatement) node.getBody());
				break;
			}// end switch
		} // end if
	}// end doLabeledStatement

	/**
	 * examines and logs control structures found within a {@code method} block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doMethodDeclaration(MethodDeclaration method, MethodDeclaration node) throws JavaModelException {
		generate(node);
	}// end doMethodDeclaration

	/**
	 * generates a logging statement for a {@code method} invocation statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.MethodInvocation} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doMethodInvocation(MethodDeclaration method, MethodInvocation node) throws JavaModelException {
		AST ast = node.getAST();
		Block block = findParentBlock(node);
		if (null != block) {
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(block.getParent().getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_method, node));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertBefore(ast.newExpressionStatement(invocation), node.getParent(), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
		} // end if
	}// end doMethodInvocation

	/**
	 * writes the value of the {@code return} statement wrapped in an {@code if}
	 * control block and/or the exiting logging statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 * <p>
	 * see the following methods for more options:
	 * </p>
	 * <ul>
	 * <li>{@link #doWriteExitIf(org.eclipse.jdt.core.dom.MethodDeclaration)}</li>
	 * <li>{@link #doWriteReturn(org.eclipse.jdt.core.dom.MethodDeclaration, org.eclipse.jdt.core.dom.ReturnStatement)}</li>
	 * <li>{@link #doWriteExit(org.eclipse.jdt.core.dom.MethodDeclaration)}</li>
	 * </ul>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.ReturnStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doReturnStatement(MethodDeclaration method, ReturnStatement node) throws JavaModelException {
		AST ast = node.getAST();
		Block block = findParentBlock(node);
		if (null != block) {
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			if (!OFF.equals(preferences.getString(EXIT_LEVEL))) {
				IfStatement ifstatement = doWriteExitIf(method);
				if (preferences.getBoolean(INCLUDE_RET_VAL) && !isVoidReturn(method)) {
					ReturnStatement newreturn = null;
					Expression expression = node.getExpression();
					if (!(expression.getNodeType() == SIMPLE_NAME) && !(expression.getNodeType() == NULL_LITERAL)) {
						VariableDeclarationStatement variable = createVariableDeclarationStatement(method, expression);
						if (null != variable) {
							// Write an in-line comment to note that code is being generated.
							Statement comment = (Statement) rewrite.getASTRewrite()
									.createStringPlaceholder(LUMBERJACK_logging_msg_comment, EMPTY_STATEMENT);
							rewrite.insertBefore(comment, node, null);
							rewrite.insertBefore(variable, node, null);
							VariableDeclarationFragment fragment = (VariableDeclarationFragment) variable.fragments()
									.get(0);
							expression = fragment.getName();
							newreturn = (ReturnStatement) ASTRewrite.create(node.getAST()).createCopyTarget(node);
							newreturn.setExpression(
									(Expression) Expression.copySubtree(ast, ast.newSimpleName(expression.toString())));
						} // end if/else
					} // end if
					MethodInvocation invocation;
					if (expression.getNodeType() == NULL_LITERAL) {
						invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME),
								preferences.getString(EXIT_LEVEL).toLowerCase(),
								createStringLiteral(ast, getReturnValueLogStatement() + expression));
					} else {
						invocation = createMethodInvocation(method.getAST(), preferences.getString(LOGGER_VAR_NAME),
								preferences.getString(EXIT_LEVEL).toLowerCase(), null);
						InfixExpression infix = ast.newInfixExpression();
						infix.setLeftOperand(
								createStringLiteral(ast, getReturnValueLogStatement() + expression + EQUALS));
						infix.setOperator(InfixExpression.Operator.PLUS);
						if (method.getReturnType2().isArrayType()) {
							infix.setRightOperand(
									createMethodInvocation(ast, Operation.getImportRewrite().addImport(ARRAYS),
											TO_STRING, ast.newSimpleName(expression.toString())));
						} else {
							infix.setRightOperand(createMethodInvocation(ast, STRING, VALUE_OF,
									ast.newSimpleName(expression.toString())));
						} // end if/else
						invocation.arguments().add(infix);
					} // end if/else
					if (null != ifstatement) {
						block = ast.newBlock();
						block.statements().add(ast.newExpressionStatement(invocation));
						ifstatement.setThenStatement(block);
						rewrite.insertBefore(ifstatement, node, null);
					} else {
						rewrite.insertBefore(ast.newExpressionStatement(invocation), node, null);
					} // end if/else
					if (null != newreturn) {
						rewrite.insertAfter(newreturn, node, null);
						// Note: Any comments after a return statement are lost when the original
						// return statement is removed.
						rewrite.remove(node, null);
					} // end if
				} // end if
					// edits.add(rewrite.getASTRewrite().rewriteAST());
				doWriteExit(method, rewrite, node);
			} // end if
		} // end if
	}// end doReturnStatement

	/**
	 * generates a logging statement for a {@code super} method invocation statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.SuperMethodInvocation} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doSuperMethodInvocation(MethodDeclaration method, SuperMethodInvocation node)
			throws JavaModelException {
		AST ast = node.getAST();
		Block block = findParentBlock(node);
		if (null != block) {
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(block.getParent().getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_method, node));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertBefore(ast.newExpressionStatement(invocation), node.getParent(), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
		} // end if
	}// end doSuperMethodInvocation

	/**
	 * examines and logs control structures found within a {@code switch} block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.SwitchStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doSwitchStatement(MethodDeclaration method, SwitchStatement node) throws JavaModelException {
		ListRewrite rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node,
				SwitchStatement.STATEMENTS_PROPERTY);
		int index = 0;
		ASTNode child;
		AST ast;
		String level;
		while (index < rewrite.getRewrittenList().size()) {
			child = (ASTNode) rewrite.getRewrittenList().get(index);
			// This test keeps generated values from being examined and logged.
			if (null != child.getParent()) {
				ast = child.getAST();
				level = getLevel(child.getParent().getNodeType(), child);
				if (!OFF.equalsIgnoreCase(level)) {
					switch (child.getNodeType()) {
					case BREAK_STATEMENT: {
						StringLiteral literal = createStringLiteral(ast, LUMBERJACK_logging_msg_break);
						MethodInvocation invocation = createMethodInvocation(ast,
								preferences.getString(LOGGER_VAR_NAME), level, literal);
						rewrite.insertBefore(ast.newExpressionStatement(invocation), child, null);
						edits.add(rewrite.getASTRewrite().rewriteAST());
						rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node,
								SwitchStatement.STATEMENTS_PROPERTY);
					} // end nested block
						break;
					case DO_STATEMENT:
						doDoStatement(method, (DoStatement) child);
						break;
					case ENHANCED_FOR_STATEMENT:
						doEnhancedForStatement(method, (EnhancedForStatement) child);
						break;
					case EXPRESSION_STATEMENT: {
						Expression expression = ((ExpressionStatement) child).getExpression();
						if (null != expression) {
							if (expression.getNodeType() == ASSIGNMENT) {
								expression = ((Assignment) expression).getRightHandSide();
								if (expression.getNodeType() == CLASS_INSTANCE_CREATION) {
									AnonymousClassDeclaration declaration = ((ClassInstanceCreation) expression)
											.getAnonymousClassDeclaration();
									if (null != declaration) {
										// Anonymous inner class logging logic.
									} // end if
								} // end if
							} else {
								switch (expression.getNodeType()) {
								case METHOD_INVOCATION:
								case SUPER_METHOD_INVOCATION:
									StringLiteral literal = createStringLiteral(ast,
											MessageFormat.format(LUMBERJACK_logging_msg_method, child));
									MethodInvocation invocation = createMethodInvocation(ast,
											preferences.getString(LOGGER_VAR_NAME), level, literal);
									rewrite.insertBefore(ast.newExpressionStatement(invocation), child, null);
									edits.add(rewrite.getASTRewrite().rewriteAST());
									rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node,
											SwitchStatement.STATEMENTS_PROPERTY);
									break;
								}// end switch
							} // end if/else
						} // end if
					} // end nested block
						break;
					case FOR_STATEMENT:
						doForStatement(method, (ForStatement) child);
						break;
					case IF_STATEMENT:
						doIfStatement(method, (IfStatement) child);
						break;
					case LABELED_STATEMENT:
						doLabeledStatement(method, (LabeledStatement) child);
						break;
					case RETURN_STATEMENT:
						break;
					case SWITCH_CASE: {
						@SuppressWarnings("deprecation")
						StringLiteral literal = createStringLiteral(ast,
								MessageFormat.format(LUMBERJACK_logging_msg_switch,
										(null == ((SwitchCase) child).getExpression() ? DEFAULT
												: ((SwitchCase) child).getExpression())));
						MethodInvocation invocation = createMethodInvocation(ast,
								preferences.getString(LOGGER_VAR_NAME), level, literal);
						rewrite.insertAfter(ast.newExpressionStatement(invocation), child, null);
						edits.add(rewrite.getASTRewrite().rewriteAST());
						rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node,
								SwitchStatement.STATEMENTS_PROPERTY);
					} // end nested block
						break;
					case SWITCH_STATEMENT:
						doSwitchStatement(method, (SwitchStatement) child);
						break;
					case SYNCHRONIZED_STATEMENT:
						doSynchronizedStatement(method, (SynchronizedStatement) child);
						break;
					case TRY_STATEMENT:
						doTryStatement(method, (TryStatement) child);
						break;
					case WHILE_STATEMENT:
						doWhileStatement(method, (WhileStatement) child);
						break;
					case VARIABLE_DECLARATION_STATEMENT: {
						if (!((VariableDeclarationStatement) child).fragments().isEmpty()) {
							VariableDeclarationFragment fragment;
							StringLiteral literal;
							MethodInvocation invocation;
							List<VariableDeclarationFragment> fragments = ((VariableDeclarationStatement) child)
									.fragments();
							for (int i = 0, j = fragments.size(); i < j; i++) {
								fragment = (VariableDeclarationFragment) ((VariableDeclarationStatement) child)
										.fragments().get(i);
								literal = createStringLiteral(ast,
										MessageFormat.format(LUMBERJACK_logging_msg_variable, fragment.getName()));
								invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
										null);
								invocation.arguments().add(literal);
								rewrite.insertBefore(ast.newExpressionStatement(invocation), child, null);
							} // end for
							edits.add(rewrite.getASTRewrite().rewriteAST());
							rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node,
									SwitchStatement.STATEMENTS_PROPERTY);
						} // end if
					} // end nested block
						break;
					}// end switch
				} // end if
			} // end if
				// Increment outside of the switch to see the next statement of the iteration.
			index++;
		} // end while
	}// end doSwitchStatement

	/**
	 * generates a logging statement for a {@code synchronized} statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.SynchronizedStatement} to
	 *               examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doSynchronizedStatement(MethodDeclaration method, SynchronizedStatement node)
			throws JavaModelException {
		AST ast = node.getAST();
		if (node.getBody().getNodeType() == BLOCK) {
			Block block = node.getBody();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast, LUMBERJACK_logging_msg_synchronized);
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
		} // end if
	}// end doSynchronizedStatement

	/**
	 * examines and logs control structures found within a {@code try/catch/finally}
	 * block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.TryStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doTryStatement(MethodDeclaration method, TryStatement node) throws JavaModelException {
		ListRewrite rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node.getBody(),
				Block.STATEMENTS_PROPERTY);
		doBody(method, rewrite);
		if (null != node.catchClauses() && !node.catchClauses().isEmpty()) {
			List<CatchClause> catchClauses = node.catchClauses();
			for (int i = 0, j = catchClauses.size(); i < j; i++) {
				rewrite = ASTRewrite.create(catchClauses.get(i).getAST()).getListRewrite(catchClauses.get(i).getBody(),
						Block.STATEMENTS_PROPERTY);
				doBody(method, rewrite);
			} // end for
		} // end if
		if (null != node.getFinally()) {
			rewrite = ASTRewrite.create(node.getAST()).getListRewrite(node.getFinally(), Block.STATEMENTS_PROPERTY);
			doBody(method, rewrite);
		} // end if
	}// end doTryStatement

	/**
	 * identifies variable declaration statements fragment(s) and generates logging
	 * for each
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the
	 *               {@link org.eclipse.jdt.core.dom.VariableDeclarationStatement}
	 *               to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	@SuppressWarnings("unchecked")
	protected void doVariableDeclarationStatement(MethodDeclaration method, VariableDeclarationStatement node)
			throws JavaModelException {
		if (!node.fragments().isEmpty()) {
			AST ast = node.getAST();
			Block block = findParentBlock(node);
			if (null != block) {
				ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
				String level = getLevel(block.getParent().getNodeType(), node);
				if (!OFF.equalsIgnoreCase(level)) {
					VariableDeclarationFragment fragment;
					StringLiteral literal;
					MethodInvocation invocation;
					List<VariableDeclarationFragment> fragments = node.fragments();
					for (int i = 0, j = fragments.size(); i < j; i++) {
						fragment = (VariableDeclarationFragment) node.fragments().get(i);
						literal = createStringLiteral(ast,
								MessageFormat.format(LUMBERJACK_logging_msg_variable, fragment.getName()));
						invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level, null);
						invocation.arguments().add(literal);
						rewrite.insertBefore(ast.newExpressionStatement(invocation), node, null);
					} // end for
					edits.add(rewrite.getASTRewrite().rewriteAST());
				} // end if
			} // end if
		} // end if
	}// end doVariableDeclarationStatement

	/**
	 * examines and logs control structures found within a {@code while} block
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @param node   the {@link org.eclipse.jdt.core.dom.WhileStatement} to examine
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doWhileStatement(MethodDeclaration method, WhileStatement node) throws JavaModelException {
		AST ast = node.getAST();
		if (node.getBody().getNodeType() == BLOCK) {
			Block block = (Block) node.getBody();
			ListRewrite rewrite = ASTRewrite.create(ast).getListRewrite(block, Block.STATEMENTS_PROPERTY);
			String level = getLevel(node.getNodeType(), node);
			if (!OFF.equalsIgnoreCase(level)) {
				StringLiteral literal = createStringLiteral(ast,
						MessageFormat.format(LUMBERJACK_logging_msg_while, node.getExpression()));
				MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), level,
						literal);
				rewrite.insertFirst(ast.newExpressionStatement(invocation), null);
				edits.add(rewrite.getASTRewrite().rewriteAST());
			} // end if
			doBody(method, rewrite);
		} // end if
	}// end doWhileStatement

	/**
	 * writes the method exiting {@code if} logging statement to contain the return
	 * value logging statement
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               generate logging for
	 * @return an instance of {@link org.eclipse.jdt.core.dom.IfStatement}
	 */
	protected IfStatement doWriteExitIf(MethodDeclaration method) {
		IfStatement statement = null;
		if (preferences.getBoolean(WRITE_EXIT_IF) && preferences.getBoolean(INCLUDE_RET_VAL) && !isVoidReturn(method)) {
			AST ast = method.getAST();
			String isMethod = getIsLoggableMethod(preferences.getString(EXIT_LEVEL));
			statement = ast.newIfStatement();
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), isMethod,
					null);
			statement.setExpression(invocation);
		} // end if
		return statement;
	}// end doWriteExitIf

	/**
	 * writes the method exiting logging statement and adds it to a
	 * {@link java.util.List} of {@link org.eclipse.jdt.core.dom.Statement}s for
	 * {@code return}
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method  the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *                generate logging for
	 * @param rewrite the {@link org.eclipse.jdt.core.dom.rewrite.ListRewrite}
	 *                accepting changes for the current method
	 * @param node    the {@link org.eclipse.jdt.core.dom.ReturnStatement} to insert
	 *                logging before
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	protected void doWriteExit(MethodDeclaration method, ListRewrite rewrite, ReturnStatement node)
			throws JavaModelException {
		if (!OFF.equals(preferences.getString(EXIT_LEVEL))) {
			AST ast = method.getAST();
			StringLiteral literal = createStringLiteral(ast,
					getExitingLogStatement() + method.getName().getFullyQualifiedName());
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME),
					preferences.getString(EXIT_LEVEL).toLowerCase(), literal);
			if (null == node) {
				rewrite.insertLast(ast.newExpressionStatement(invocation), null);
			} else {
				rewrite.insertBefore(ast.newExpressionStatement(invocation), node, null);
			} // end if
			edits.add(rewrite.getASTRewrite().rewriteAST());
		} // end if
	}// end doWriteExit

	/**
	 * creates local variable declarations to replace return values if the value is
	 * not already a variable
	 * <p>
	 * for instance, if a method is returning a call to another method or a
	 * parenthesized expression, then these values are assigned to a local variable
	 * for logging purposes
	 * </p>
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param method     the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *                   generate logging for
	 * 
	 * @param expression the {@link org.eclipse.jdt.core.dom.Expression} to insert
	 * @return a new {@link org.eclipse.jdt.core.dom.VariableDeclarationStatement}.
	 */
	protected VariableDeclarationStatement createVariableDeclarationStatement(MethodDeclaration method,
			Expression expression) {
		AST ast = expression.getAST();
		ASTNode node = ASTNode.copySubtree(ast, expression);
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName(createNameSuggestion(method)));
		fragment.setInitializer((Expression) node);
		VariableDeclarationStatement variable = ast.newVariableDeclarationStatement(fragment);
		Type type = method.getReturnType2();
		if (type.isArrayType()) {
			PrimitiveType.Code primitive = getPrimitiveType(type);
			if (null != primitive) {
				variable.setType(
						ast.newArrayType(ast.newPrimitiveType(primitive), ((ArrayType) type).dimensions().size()));
			} else {
				variable.setType(ast.newArrayType(ast.newSimpleType(ast.newName(removeBrackets(type))),
						((ArrayType) type).dimensions().size()));
			} // end if/else
		} else if (type.isParameterizedType()) {
			variable.setType((Type) Type.copySubtree(ast, type));
		} else if (type.isPrimitiveType()) {
			PrimitiveType.Code primitive = getPrimitiveType(type);
			variable.setType(ast.newPrimitiveType(primitive));
		} else if (type.isSimpleType()) {
			variable.setType(ast.newSimpleType(ast.newName(type.toString())));
		} // end if/else
		return variable;
	}// end createVariableDeclarationStatement

	/**
	 * finds the parent block statement for the current node
	 * <p>
	 * Eclipse's object structure allows for several
	 * {@link org.eclipse.jdt.core.dom.Statement}s to be nested within each other,
	 * so this method runs recursively until the parent instance of
	 * {@link org.eclipse.jdt.core.dom.Block} is found
	 * </p>
	 *
	 * @param node the {@link org.eclipse.jdt.core.dom.ASTNode} to examine
	 * @return the enclosing {@link org.eclipse.jdt.core.dom.Block} for the current
	 *         node
	 * 
	 * @throws JavaModelException exception if the statement cannot be added to the
	 *                            node
	 */
	private Block findParentBlock(ASTNode node) throws JavaModelException {
		while (null != node.getParent()) {
			return (node.getParent().getNodeType() == BLOCK) ? (Block) node.getParent()
					: findParentBlock(node.getParent());
		} // end while
		return null;
	}// end findParentBlock

	/**
	 * determines a unique variable name for creation based on existing variable
	 * names in the enclosing class structure and method
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 * 
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               examine
	 * @return a unique variable name to use for generated logging statements
	 */
	protected String createNameSuggestion(MethodDeclaration method) {
		String result = method.getName().toString();
		if (null != excluded && !excluded.isEmpty()) {
			String variable = "lj";
			if (null != result && result.length() > 0) {
				variable += String.valueOf(result.charAt(0)).toUpperCase() + result.substring(1);
			} // end if
			List<String> alike = new ArrayList<String>();
			for (Iterator<String> iter = excluded.getExcludedNames().iterator(); iter.hasNext();) {
				if (iter.next().startsWith(variable)) {
					alike.add(variable);
				} // end if
			} // end for
			if (!alike.isEmpty()) {
				variable += String.valueOf(alike.size());
			} // end if
			return excluded.addExcludedLocalName(variable);
		} // end if
		return result;
	}// end createNameSuggestion

	/**
	 * identifies the client selected logging level to use for the current node
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 * 
	 * @param nodeType the {@code int} value of the node type
	 * @param node     the {@link org.eclipse.jdt.core.dom.ASTNode} to find the
	 *                 level for
	 * @return {@code String} value of the appropriate logging level
	 */
	protected String getLevel(int nodeType, ASTNode node) {
		String level = OFF;
		switch (nodeType) {
		case DO_STATEMENT:
			level = preferences.getString(WRITE_DO_WHILE).toLowerCase();
			break;
		case ENHANCED_FOR_STATEMENT:
		case FOR_STATEMENT:
			level = preferences.getString(WRITE_FOR).toLowerCase();
			break;
		case IF_STATEMENT:
			level = preferences.getString(WRITE_IF_ELSE).toLowerCase();
			break;
		case TRY_STATEMENT:
		case CATCH_CLAUSE:
			level = preferences.getString(WRITE_TRY_CATCH).toLowerCase();
			break;
		case WHILE_STATEMENT:
			level = preferences.getString(WRITE_WHILE).toLowerCase();
			break;
		case SWITCH_STATEMENT:
			level = preferences.getString(WRITE_SWITCH).toLowerCase();
			break;
		case VARIABLE_DECLARATION_STATEMENT:
			level = preferences.getString(WRITE_VARIABLE).toLowerCase();
			break;
		case METHOD_DECLARATION:
			if (null != node) {
				if (node.getNodeType() == BREAK_STATEMENT
						&& !OFF.equalsIgnoreCase(preferences.getString(WRITE_SWITCH))) {
					level = preferences.getString(WRITE_SWITCH).toLowerCase();
				} else if ((node.getNodeType() == METHOD_INVOCATION || node.getNodeType() == SUPER_METHOD_INVOCATION)
						&& !OFF.equalsIgnoreCase(preferences.getString(WRITE_METHOD_CALL))) {
					level = preferences.getString(WRITE_METHOD_CALL).toLowerCase();
				} else if (node.getNodeType() == SWITCH_STATEMENT
						&& !OFF.equalsIgnoreCase(preferences.getString(WRITE_SWITCH))) {
					level = preferences.getString(WRITE_SWITCH).toLowerCase();
				} else if (node.getNodeType() == VARIABLE_DECLARATION_STATEMENT
						&& !OFF.equalsIgnoreCase(preferences.getString(WRITE_VARIABLE))) {
					level = preferences.getString(WRITE_VARIABLE).toLowerCase();
				} // end if/else
			} // end if
			break;
		case METHOD_INVOCATION:
		case SUPER_METHOD_INVOCATION:
			level = preferences.getString(WRITE_METHOD_CALL).toLowerCase();
			break;
		case SYNCHRONIZED_STATEMENT:
			level = preferences.getString(WRITE_SYNCHRONIZED).toLowerCase();
			break;
		default:
			level = OFF;
			break;
		}// end switch
		return level;
	}// end getLevel

	/**
	 * creates a simple {@code method} invocation
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param ast        the enclosing {@link org.eclipse.jdt.core.dom.AST} node to
	 *                   add to
	 * @param name       simple name for the invocation
	 * @param methodName method name of the invocation
	 * @param expression expression for the invocation
	 * @return a new {@link org.eclipse.jdt.core.dom.MethodInvocation}
	 */
	@SuppressWarnings("unchecked")
	protected MethodInvocation createMethodInvocation(AST ast, String name, String methodName, Expression expression) {
		MethodInvocation invocation = ast.newMethodInvocation();
		invocation.setExpression(ast.newName(name));
		invocation.setName(ast.newSimpleName(methodName));
		if (null != expression) {
			invocation.arguments().add(expression);
		} // end if
		return invocation;
	}// end getMethodInvocation

	/**
	 * creates a {@code String} literal
	 * <p>
	 * this method may be overridden in sub-classes to provide a different format or
	 * statement(s)
	 * </p>
	 *
	 * @param ast   the enclosing {@link org.eclipse.jdt.core.dom.AST} node to add
	 *              to
	 * @param value value of the {@code String} literal
	 * @return a new {@link org.eclipse.jdt.core.dom.StringLiteral}
	 */
	protected StringLiteral createStringLiteral(AST ast, String value) {
		StringLiteral literal = ast.newStringLiteral();
		literal.setLiteralValue(sanitizeLogMessage(value));
		return literal;
	}// end createStringLiteral

	/**
	 * removes formatting artifacts from source snippets before they are embedded in
	 * generated logging statements.
	 *
	 * @param value the log message value to sanitize
	 * @return the sanitized log message value
	 */
	protected String sanitizeLogMessage(String value) {
		if (value == null) {
			return "";
		} // end if
		return value.strip().replaceAll("\\s*\\R\\s*", " ");
	}// end sanitizeLogMessage

	/**
	 * determines if a {@code method} has a {@code void} {@code return} type
	 *
	 * @param method the {@link org.eclipse.jdt.core.dom.MethodDeclaration} to
	 *               examine
	 * @return {@code true} if the {@code method} has a {@code void} {@code return}
	 *         type
	 */
	protected boolean isVoidReturn(MethodDeclaration method) {
		ITypeBinding type = method.getReturnType2().resolveBinding();
		return null != type && VOID.equals(type.getName());
	}// end isVoidReturn

	/**
	 * determines the primitive {@code return} type of the parameter
	 * 
	 * @param type the {@link org.eclipse.jdt.core.dom.Type} to examine
	 * @return the {@link org.eclipse.jdt.core.dom.PrimitiveType.Code}
	 */
	protected Code getPrimitiveType(Type type) {
		String value = removeBrackets(type);
		if (BYTE.equals(value)) {
			return PrimitiveType.BYTE;
		} else if (SHORT.equals(value)) {
			return PrimitiveType.SHORT;
		} else if (FLOAT.equals(value)) {
			return PrimitiveType.FLOAT;
		} else if (INT.equals(value)) {
			return PrimitiveType.INT;
		} else if (CHAR.equals(value)) {
			return PrimitiveType.CHAR;
		} else if (DOUBLE.equals(value)) {
			return PrimitiveType.DOUBLE;
		} else if (LONG.equals(value)) {
			return PrimitiveType.LONG;
		} else if (BOOLEAN.equals(value)) {
			return PrimitiveType.BOOLEAN;
		} // end if/else
		return null;
	}// end getPrimitiveType

	/**
	 * gets the {@code String} value of the parameter type and replaces any bracket
	 * characters
	 *
	 * @param type the name of the {@link org.eclipse.jdt.core.dom.Type}
	 * @return the {@code String} value of the type name
	 */
	protected String removeBrackets(Type type) {
		return type.toString().replaceAll("\\[", "").replaceAll("]", "");
	}// end removeBrackets

	/**
	 * sets the {@code ExcludedNamesMap} of variable names to be excluded when
	 * generating a variable name for the logging statements
	 *
	 * @param excluded the
	 *                 {@link com.all9ssolutions.lumberjack.generators.ExcludedNamesMap}
	 *                 to set
	 */
	public void setExcludedNames(ExcludedNamesMap excluded) {
		this.excluded = excluded;
	}// end setExcludedNames
}// end class

/**
 * This class is used to identify valid Javadoc comments and clean the comments
 * of nested Javadoc tags and supported HTML tags.
 *
 */
class JavadocParser {
	private static final String[] JAVADOC_TAGS;
	private static final List<String> NESTED_JAVADOC_TAGS;
	private static final List<String> SUPPORTED_HTML_TAGS;

	static {
		JAVADOC_TAGS = new String[] { "@author", "@category", "@deprecated", "@exception", "@param", "@return", "@see",
				"@serialData", "@since", "@throws" };
		NESTED_JAVADOC_TAGS = Arrays.asList(new String[] { "{@code ", "{@docRoot ", "{@inheritDoc ", "{@linkplain ",
				"{@link ", "{@literal ", "{@value " });
		SUPPORTED_HTML_TAGS = Arrays.asList(new String[] { "a", "b", "blockquote", "br", "code", "dd", "dl", "dt", "em",
				"h1", "h2", "h3", "h4", "h5", "h6", "hr", "i", "li", "nl", "ol", "p", "pre", "q", "strong", "tbody",
				"td", "th", "tr", "tt", "ul", "/a", "/b", "/blockquote", "br/", "/code", "/dd", "/dl", "/dt", "/em",
				"/h1", "/h2", "/h3", "/h4", "/h5", "/h6", "hr/", "/i", "/li", "/nl", "/ol", "/p", "/pre", "/q",
				"/strong", "/tbody", "/td", "/th", "/tr", "/tt", "/ul" });
	}// end static block

	/**
	 * tests if the parameter comment starts with a Javadoc
	 * <p>
	 * <b>this method should be called before calling the {@link #clean(String)}
	 * method.</b>
	 * </p>
	 *
	 * @param comment the {@code String} value of the comment to test
	 * @return {@code true} if the parameter is a comment
	 */
	static boolean is(String comment) {
		if (null != comment) {
			for (int i = 0, j = JAVADOC_TAGS.length; i < j; i++) {
				if (comment.startsWith(JAVADOC_TAGS[i])) {
					return false;
				} // end if
			} // end for
			return true;
		} // end if
		return false;
	}// end is

	/**
	 * removes nested Javadoc tags and supported HTML tags
	 * <p>
	 * <b>if the parameter comment is {@code null}, then {@code null} will be
	 * returned.</b>
	 * </p>
	 * 
	 * @param comment the {@code String} value of the comment to remove tags from
	 * @return the cleaned comment
	 */
	static String clean(String comment) {
		if (null != comment) {
			comment = removeJavadocTags(comment);
			comment = removeHTMLTags(comment);
		} // end if
		return comment;
	}// end clean

	/**
	 * parses nested Javadoc tags and removes them from the comment
	 *
	 * @param comment the {@code String} value of the comment to remove tags from
	 * @return the cleaned comment
	 */
	private static String removeJavadocTags(String comment) {
		StringBuilder sb = new StringBuilder();
		String token;
		StringTokenizer tokenizer = new StringTokenizer(comment, " ");
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (!NESTED_JAVADOC_TAGS.contains(token)) {
				sb.append(token);
				if (tokenizer.hasMoreTokens()) {
					sb.append(" ");
				} // end if
			} // end if
		} // end while
		comment = sb.toString().replaceAll("\\}", "");
		return sb.toString();
	}// end removeJavadocTags

	/**
	 * parses the Javadoc supported HTML tags and removes them from the comment
	 *
	 * @param comment the {@code String} value of the comment to remove tags from
	 * @return the cleaned comment
	 */
	private static String removeHTMLTags(String comment) {
		StringBuilder sb = new StringBuilder();
		String token;
		StringTokenizer tokenizer = new StringTokenizer(comment, "<>");
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (!SUPPORTED_HTML_TAGS.contains(token)) {
				sb.append(token);
			} // end if
		} // end while
		return sb.toString();
	}// end removeHTML
}// end class
