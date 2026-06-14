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
package com.all9ssolutions.ender.business;

import static com.all9ssolutions.ender.Activator.PLUGIN_ID;
import static com.all9ssolutions.ender.Messages.ENDER_actual_multiline_comment;
import static com.all9ssolutions.ender.Messages.ENDER_multiline_label;
import static com.all9ssolutions.ender.Messages.ENDER_actual_inline_comment;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

import com.all9ssolutions.ender.Activator;
import com.all9ssolutions.ender.preferences.PreferenceConstants;

/**
 * This class is used to do the work portion of generating end comments for
 * methods and control statements for the Java class in the current editor.
 *
 */
public class Operation implements IWorkspaceRunnable, PreferenceConstants {
	private ICompilationUnit iunit;
	private String newline;
	private IBuffer contents;
	private NoDuplicatesList edits;
	private CompilationUnit unit;

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
		newline = iunit.findRecommendedLineSeparator();
		contents = iunit.getBuffer();
		edits = new NoDuplicatesList();
		try {
			unit = new Parser().parse(type.getCompilationUnit());
		} catch (MalformedTreeException e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} // end try/catch
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask("Generating end comments for the class...", 1);
			for (int i = 0, j = unit.types().size(); i < j; i++) {
				end((TypeDeclaration) unit.types().get(i));
				endType((TypeDeclaration) unit.types().get(i));
			} // end for
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} finally {
			monitor.done();
		} // end try/catch/finally
	}// end run

	/**
	 * examines the instance of {@link org.eclipse.jdt.core.dom.TypeDeclaration} and
	 * adds trailing comments to declarations and statements
	 * 
	 * @param declaration the Java class in the current editor
	 */
	@SuppressWarnings("unchecked")
	private void end(TypeDeclaration declaration) {
		end(declaration.bodyDeclarations());
	}// end end

	/**
	 * iterate through the list of {@link org.eclipse.jdt.core.dom.BodyDeclaration}s
	 * and adds trailing comments to each
	 * 
	 * @param declarations the {@code List} of declarations to iterate through
	 */
	private void end(List<BodyDeclaration> declarations) {
		if (null != declarations && !declarations.isEmpty()) {
			for (int i = 0, j = declarations.size(); i < j; i++) {
				if (declarations.get(i).getNodeType() == ASTNode.TYPE_DECLARATION) {
					end((TypeDeclaration) declarations.get(i));
					endType((TypeDeclaration) declarations.get(i));
				} else if (declarations.get(i).getNodeType() == ASTNode.FIELD_DECLARATION) {
					endFieldDeclaration((FieldDeclaration) declarations.get(i));
				} else if (declarations.get(i).getNodeType() == ASTNode.METHOD_DECLARATION) {
					endMethodDeclaration((MethodDeclaration) declarations.get(i));
				} else if (declarations.get(i).getNodeType() == ASTNode.INITIALIZER) {
					endStaticBlock((Initializer) declarations.get(i));
				} // end if/else
			} // end for
		} // end if
	}// end end

	/**
	 * inserts ending comments for the parameter declaration
	 * 
	 * @param declaration the instance of
	 *                    {@link org.eclipse.jdt.core.dom.TypeDeclaration} to add
	 *                    ending comments to
	 */
	private void endType(TypeDeclaration declaration) {
		int offset = declaration.getStartPosition() + declaration.getLength();
		if (!hasEndingComment(offset)) {
			edits.add(new InsertEdit(offset, MessageFormat.format(getCommentStyle(), new Object[] { "class" })));
		} // end if
	}// end endType

	/**
	 * inserts ending comments for the parameter declaration
	 * 
	 * @param declaration the instance of
	 *                    {@link org.eclipse.jdt.core.dom.FieldDeclaration} to add
	 *                    ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endFieldDeclaration(FieldDeclaration declaration) {
		endVariableDeclarationFragments(declaration.fragments());
	}// end endFieldDeclaration

	/**
	 * inserts ending comments for the parameter declaration.
	 * 
	 * @param declaration the instance of
	 *                    {@link org.eclipse.jdt.core.dom.MethodDeclaration} to add
	 *                    ending comments to
	 */
	private void endMethodDeclaration(MethodDeclaration declaration) {
		if (!Modifier.isAbstract(declaration.getModifiers()) && declaration.getBody().getNodeType() == ASTNode.BLOCK) {
			endBlockStatement(declaration.getBody(),
					(declaration.isConstructor()) ? "constructor" : declaration.getName().toString());
		} // end if
	}// end endMethodDeclaration

	/**
	 * inserts ending comments for the parameter declaration.
	 * 
	 * @param initializer the instance of
	 *                    {@link org.eclipse.jdt.core.dom.Initializer} to add ending
	 *                    comments to
	 */
	private void endStaticBlock(Initializer initializer) {
		int offset = initializer.getStartPosition() + initializer.getLength();
		if (!hasEndingComment(offset)) {
			edits.add(new InsertEdit(offset, MessageFormat.format(getCommentStyle(), new Object[] { "static block" })));
		} // end if
	}// end endStaticBlock

	/**
	 * inserts ending comments for the parameter statement.
	 * 
	 * @param statement the instance of {@link org.eclipse.jdt.core.dom.Statement}
	 *                  to add ending comments to
	 */
	private void endStatement(Statement statement) {
		if (null != statement) {
			switch (statement.getNodeType()) {
			case ASTNode.BLOCK:
				endBlockStatement((Block) statement, "block");
				break;
			case ASTNode.DO_STATEMENT:
				endDoStatement((DoStatement) statement);
				break;
			case ASTNode.ENHANCED_FOR_STATEMENT:
				endEnhancedForStatement((EnhancedForStatement) statement);
				break;
			case ASTNode.EXPRESSION_STATEMENT:
				endExpressionStatement((ExpressionStatement) statement);
				break;
			case ASTNode.FOR_STATEMENT:
				endForStatement((ForStatement) statement);
				break;
			case ASTNode.IF_STATEMENT:
				endIfStatement((IfStatement) statement);
				break;
			case ASTNode.LABELED_STATEMENT:
				endLabeledStatement((LabeledStatement) statement);
				break;
			case ASTNode.RETURN_STATEMENT:
				endReturnStatement((ReturnStatement) statement);
				break;
			case ASTNode.SWITCH_STATEMENT:
				endSwitchStatement((SwitchStatement) statement);
				break;
			case ASTNode.SYNCHRONIZED_STATEMENT:
				endSynchronizedStatement((SynchronizedStatement) statement);
				break;
			case ASTNode.TRY_STATEMENT:
				endTryStatement((TryStatement) statement);
				break;
			case ASTNode.VARIABLE_DECLARATION_STATEMENT:
				endVariableStatement((VariableDeclarationStatement) statement);
				break;
			case ASTNode.WHILE_STATEMENT:
				endWhileStatement((WhileStatement) statement);
				break;
			}// end switch
		} // end if
	}// end endStatement

	/**
	 * inserts ending comments for the parameter block
	 * 
	 * @param block the instance of {@link org.eclipse.jdt.core.dom.Block} to add
	 *              ending comments to
	 * @param name  the name to format in the comment
	 */
	@SuppressWarnings("unchecked")
	private void endBlockStatement(Block block, String name) {
		List<Statement> statements = block.statements();
		if (!statements.isEmpty()) {
			for (int i = 0, j = statements.size(); i < j; i++) {
				endStatement(statements.get(i));
			} // end for
		} // end if
		int offset = block.getStartPosition() + block.getLength();
		if (!hasEndingComment(offset)) {
			edits.add(new InsertEdit(offset, MessageFormat.format(getCommentStyle(), new Object[] { name })));
		} // end if
	}// end endBlockStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of {@link org.eclipse.jdt.core.dom.DoStatement}
	 *                  to add ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endDoStatement(DoStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			Block block = (Block) statement.getBody();
			List<Statement> statements = block.statements();
			if (!statements.isEmpty()) {
				for (int i = 0, j = statements.size(); i < j; i++) {
					endStatement(statements.get(i));
				} // end for
			} // end if
		} // end if
	}// end endDoStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.EnhancedForStatement} to add
	 *                  ending comments to
	 */
	private void endEnhancedForStatement(EnhancedForStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			endBlockStatement((Block) statement.getBody(), "for");
		} // end if
	}// end endEnhancedForStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.ExpressionStatement} to add
	 *                  ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endExpressionStatement(ExpressionStatement statement) {
		if (statement.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION
				|| statement.getExpression().getNodeType() == ASTNode.SUPER_METHOD_INVOCATION) {
			List<ASTNode> arguments = (statement.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION)
					? ((MethodInvocation) statement.getExpression()).arguments()
					: ((SuperMethodInvocation) statement.getExpression()).arguments();
			if (null != arguments && !arguments.isEmpty()) {
				for (ASTNode node : arguments) {
					if (node.getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
						endClassInstanceCreation((Expression) node);
					} // end if
				} // end for
			} // end if
		} else if (statement.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
			endClassInstanceCreation(statement.getExpression());
		} else if (statement.getExpression().getNodeType() == ASTNode.ASSIGNMENT) {
			Assignment assignment = (Assignment) statement.getExpression();
			if (assignment.getRightHandSide().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
				endClassInstanceCreation(assignment.getRightHandSide());
			} // end if
		} // end if/else
			// end if/else
	}// end endExpressionStatement

	/**
	 * inserts ending comments for the parameter expression
	 * 
	 * @param expression the instance of {@link org.eclipse.jdt.core.dom.Expression}
	 *                   to add ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endClassInstanceCreation(Expression expression) {
		AnonymousClassDeclaration declaration = ((ClassInstanceCreation) expression).getAnonymousClassDeclaration();
		if (null != declaration) {
			end(declaration.bodyDeclarations());
		} // end if
	}// end endClassInstanceCreation

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.ForStatement} to add ending
	 *                  comments to
	 */
	private void endForStatement(ForStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			endBlockStatement((Block) statement.getBody(), "for");
		} // end if
	}// end endForStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of {@link org.eclipse.jdt.core.dom.IfStatement}
	 *                  to add ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endIfStatement(IfStatement statement) {
		if (statement.getThenStatement().getNodeType() == ASTNode.BLOCK) {
			Block block = (Block) statement.getThenStatement();
			List<Statement> statements = block.statements();
			if (!statements.isEmpty()) {
				for (int i = 0, j = statements.size(); i < j; i++) {
					endStatement(statements.get(i));
				} // end for
			} // end if
			if (null != statement.getElseStatement()) {
				if (statement.getElseStatement().getNodeType() == ASTNode.IF_STATEMENT) {
					endIfStatement((IfStatement) statement.getElseStatement());
				} else {
					if (statement.getElseStatement().getNodeType() == ASTNode.BLOCK) {
						block = (Block) statement.getElseStatement();
						statements = block.statements();
						if (!statements.isEmpty()) {
							for (int i = 0, j = statements.size(); i < j; i++) {
								endStatement(statements.get(i));
							} // end for
						} // end if
					} // end if
				} // end if/else
			} // end if
			int offset = statement.getStartPosition() + statement.getLength();
			if (!hasEndingComment(offset)) {
				edits.add(new InsertEdit(offset, MessageFormat.format(getCommentStyle(),
						(null != statement.getElseStatement()) ? new Object[] { "if/else" } : new Object[] { "if" })));
			} // end if
		} // end if
	}// end endIfStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.LabeledStatement} to add
	 *                  ending comments to
	 */
	private void endLabeledStatement(LabeledStatement statement) {
		endStatement(statement.getBody());
	}// end endLabeledStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.ReturnStatement} to add
	 *                  ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endReturnStatement(ReturnStatement statement) {
		if (null != statement.getExpression()) {
			if (statement.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
				endClassInstanceCreation(statement.getExpression());
			} else if (statement.getExpression().getNodeType() == ASTNode.ARRAY_CREATION) {
				ArrayCreation creation = (ArrayCreation) statement.getExpression();
				if (null != creation && null != creation.getInitializer()) {
					List<ASTNode> expressions = creation.getInitializer().expressions();
					if (null != expressions && !expressions.isEmpty()) {
						for (ASTNode node : expressions) {
							if (node.getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
								endClassInstanceCreation((Expression) node);
							} // end if
						} // end for
					} // end if
				} // end if
			} // end if/else
		} // end if
	}// end endReturnStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.SwitchStatement} to add
	 *                  ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endSwitchStatement(SwitchStatement statement) {
		List<Statement> statements = statement.statements();
		if (!statements.isEmpty()) {
			for (int i = 0, j = statements.size(); i < j; i++) {
				endStatement(statements.get(i));
			} // end for
		} // end if
		int offset = statement.getStartPosition() + statement.getLength();
		if (!hasEndingComment(offset)) {
			edits.add(new InsertEdit(offset, MessageFormat.format(getCommentStyle(), new Object[] { "switch" })));
		} // end if
	}// end endSwitchStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.SynchronizedStatement} to
	 *                  add ending comments to
	 */
	private void endSynchronizedStatement(SynchronizedStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			endBlockStatement((Block) statement.getBody(), "synchronized");
		} // end if
	}// end endSynchronizedStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.TryStatement} to add ending
	 *                  comments to
	 */
	@SuppressWarnings("unchecked")
	private void endTryStatement(TryStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			Block block = statement.getBody();
			List<Statement> statements = block.statements();
			for (int i = 0, j = statements.size(); i < j; i++) {
				endStatement(statements.get(i));
			} // end for
		} // end if
		if (null != statement.getFinally()
				&& (null != statement.catchClauses() && !statement.catchClauses().isEmpty())) {
			endBlockStatement((Block) statement.getFinally(), "try/catch/finally");
		} else if (null != statement.getFinally()
				&& (null == statement.catchClauses() || statement.catchClauses().isEmpty())) {
			endBlockStatement((Block) statement.getFinally(), "try/finally");
		} else if (null != statement.catchClauses() && !statement.catchClauses().isEmpty()) {
			CatchClause clause = (CatchClause) statement.catchClauses().get(statement.catchClauses().size() - 1);
			endBlockStatement((Block) clause.getBody(), "try/catch");
		} else {
			endBlockStatement((Block) statement.getBody(), "try");
		} // end if/else
	}// end endTryStatement

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.VariableDeclarationStatement}
	 *                  to add ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endVariableStatement(VariableDeclarationStatement statement) {
		if (statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT) {
			endVariableDeclarationFragments(statement.fragments());
		} // end if
	}// end endVariableStatement

	/**
	 * inserts ending comments for the parameter fragments
	 * 
	 * @param fragments the {@link List} of {@link VariableDeclarationFragment}s to
	 *                  add ending comments to
	 */
	@SuppressWarnings("unchecked")
	private void endVariableDeclarationFragments(List<VariableDeclarationFragment> fragments) {
		if (null != fragments && !fragments.isEmpty()) {
			for (VariableDeclarationFragment fragment : fragments) {
				if (null != fragment.getInitializer()) {
					if (fragment.getInitializer().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
						endClassInstanceCreation(fragment.getInitializer());
					} else if (fragment.getInitializer().getNodeType() == ASTNode.ARRAY_CREATION
							|| fragment.getInitializer().getNodeType() == ASTNode.ARRAY_INITIALIZER) {
						List<ASTNode> expressions = null;
						if (fragment.getInitializer().getNodeType() == ASTNode.ARRAY_CREATION) {
							ArrayCreation creation = (ArrayCreation) fragment.getInitializer();
							if (null != creation && null != creation.getInitializer()) {
								expressions = creation.getInitializer().expressions();
							} // end if
						} else if (fragment.getInitializer().getNodeType() == ASTNode.ARRAY_INITIALIZER) {
							expressions = ((ArrayInitializer) fragment.getInitializer()).expressions();
						} // end if/else
						if (null != expressions && !expressions.isEmpty()) {
							for (ASTNode node : expressions) {
								if (node.getNodeType() == ASTNode.CLASS_INSTANCE_CREATION) {
									endClassInstanceCreation((Expression) node);
								} // end if
							} // end for
						} // end if
					} // end if/else
				} // end if
			} // end for
		} // end if
	}// end endVariableDeclarationFragments

	/**
	 * inserts ending comments for the parameter statement
	 * 
	 * @param statement the instance of
	 *                  {@link org.eclipse.jdt.core.dom.WhileStatement} to add
	 *                  ending comments to
	 */
	private void endWhileStatement(WhileStatement statement) {
		if (statement.getBody().getNodeType() == ASTNode.BLOCK) {
			endBlockStatement((Block) statement.getBody(), "while");
		} // end if
	}// end endWhileStatement

	/**
	 * gets the preferred comment style from the preference store
	 * 
	 * @return the preferred comment style
	 */
	private String getCommentStyle() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String type = store.getString(PREF_COMMENT_TYPE);
		if (null == type || "".equals(type)) {
			type = store.getDefaultString(PREF_COMMENT_TYPE);
		} // end if
		return (ENDER_multiline_label.equals(type)) //
				? ENDER_actual_multiline_comment //
				: ENDER_actual_inline_comment;
	}// end getCommentStyle

	/**
	 * finds the remainder of a line following the closing brace of a statement and
	 * tests whether an ending comment already exists
	 * 
	 * @param offset the position of the closing brace
	 * @return {@code true} if an ending comment is found
	 */
	private boolean hasEndingComment(int offset) {
		String test = "";
		String text = contents.getText(offset, contents.getLength() - offset);
		char[] chars = text.toCharArray();
		int i = 0;
		for (int j = chars.length, k = contents.getLength(); i < j && i < k; i++) {
			if (test.endsWith(newline)) {
				break;
			} // end if
			test += String.valueOf(chars[i]);
		} // end if
		return test.contains("//") || test.contains("/*");
	}// end hasEndingComment

	/**
	 * compiles all of the text edit changes in the same compilation unit
	 * 
	 * @return an instance of
	 *         {@link org.eclipse.jdt.core.refactoring.CompilationUnitChange}
	 * @throws CoreException exception if the text edits cannot be compiled
	 */
	public CompilationUnitChange createChange() throws CoreException {
		CompilationUnitChange change = new CompilationUnitChange(iunit.getElementName(), iunit);
		change.setKeepPreviewEdits(true);
		change.setEdit(new MultiTextEdit());
		for (int i = 0, j = edits.size(); i < j; i++) {
			change.getEdit().addChild(edits.get(i));
			change.addTextEditGroup(new TextEditGroup("Update Comment", edits.get(i)));
		} // end for
		return change;
	}// end createChange

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
	 * This nested class is used to keep duplicate instances of
	 * {@link org.eclipse.text.edits.TextEdit}s from being added to a list as
	 * duplicates would cause the modification logic to fail.
	 * 
	 */
	private class NoDuplicatesList extends ArrayList<TextEdit> {
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.ArrayList#add(java.lang.Object)
		 */
		@Override
		public boolean add(TextEdit e) {
			boolean modified = false;
			TextEdit edit;
			for (int i = 0, j = size(); i < j; i++) {
				edit = get(i);
				if (edit.getOffset() == e.getOffset()) {
					super.add(i, e);
					super.remove(++i);
					modified = true;
					break;
				} // end if
			} // end for
			return (modified) ? false : super.add(e);
		}// end add
	}// end class
}// end class
