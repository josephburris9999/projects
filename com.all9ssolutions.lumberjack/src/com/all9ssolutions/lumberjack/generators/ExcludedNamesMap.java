/**
 * @(#)ExcludedNamesMap.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import static org.eclipse.jdt.core.dom.ASTNode.BLOCK;
import static org.eclipse.jdt.core.dom.ASTNode.CATCH_CLAUSE;
import static org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.IF_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.LABELED_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.SINGLE_VARIABLE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.SWITCH_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TRY_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * This class is used to model a map of variable names to be excluded when
 * generating a temporary variable name.
 * <p>
 * A temporary variable name is needed in the case that a {@code return}
 * statement's expression is a literal value or method call rather than a
 * variable name. A temporary variable is created to hold these values in order
 * to allow logging without interfering with the method logic.
 * </p>
 * <p>
 * Variable names are defined with the following rules:
 * </p>
 * <ul>
 * <li>A variable name must begin with a letter and must be a sequence of
 * letters or digits. Note that the terms "letter" and "digit" are much broader
 * in Java than in most languages. A letter is defined as 'A'–'Z', 'a'–'z', '_',
 * '$', or any Unicode character that denotes a letter in a language. For
 * example, German users can use umlauts such as 'ä' in variable names; Greek
 * speakers could use a π. Similarly, digits are '0'–'9' and any Unicode
 * characters that denote a digit in a language.</li>
 * <li>Symbols like '+' or '©' cannot be used inside variable names, nor can
 * spaces.</li>
 * <li>All characters in the name of a variable are significant and case is also
 * significant.</li>
 * <li>The length of a variable name is essentially unlimited.</li>
 * </ul>
 * 
 * @see java.util.HashMap
 */
public class ExcludedNamesMap extends HashMap<String, Set<String>> implements Cloneable {
	private static final long serialVersionUID = 1L;
	private final String clazz = "CLASS";
	private final String local = "LOCAL";

	/**
	 * default constructor instantiates this class
	 */
	public ExcludedNamesMap() {
		super(2);
		put(clazz, new HashSet<>());
		put(local, new HashSet<>());
	}// end constructor

	/**
	 * loads the variable names found in the current class, the current class' super
	 * class (if any), and the current class' parent class (if any), as well as the
	 * names of the class variables in each to identify a list of variable names to
	 * be avoided for generating variables
	 *
	 * @param type the {@link org.eclipse.jdt.core.dom.TypeDeclaration} to examine
	 */
	public void loadExcludedClassNames(TypeDeclaration type) {
		if (null != type) {
			Set<String> set = get(clazz);
			ITypeBinding binding = type.resolveBinding();
			// Get the current class.
			set.add(type.getName().toString());
			IVariableBinding[] fields = binding.getDeclaredFields();
			for (int i = 0, j = fields.length; i < j; ++i) {
				set.add(fields[i].getName());
			} // end for
				// Get the super class for the current class.
			if (null != type.getSuperclassType()) {
				binding = type.getSuperclassType().resolveBinding();
				String name;
				while (null != binding) {
					name = binding.getBinaryName();
					if (name.indexOf(".") != -1) {
						name = name.substring(name.lastIndexOf(".") + 1);
					} // end if
					set.add(name);
					fields = binding.getDeclaredFields();
					for (int i = 0, j = fields.length; i < j; ++i) {
						if (!(Modifier.isPrivate(fields[i].getModifiers()))) {
							set.add(fields[i].getName());
						} // end if
					} // end for
					binding = binding.getSuperclass();
				} // end while
			} // end if
				// Get the parent class for the current class.
			if (null != type.getParent() && type.getParent().getNodeType() == TYPE_DECLARATION) {
				loadExcludedClassNames((TypeDeclaration) type.getParent());
			} // end if
			put(clazz, set);
		} // end if
	}// end loadExcludedClassNames

	/**
	 * loads the variable names found in the current method to identify a list of
	 * variable names to be avoided for generating variables
	 *
	 * @param parameters the list of method parameters
	 * @param statements the list of statements in the current method
	 */
	public void loadExcludedLocalNames(List<SingleVariableDeclaration> parameters, List<Statement> statements) {
		// Loop through the parameters and statement to identify the names of variables.
		Set<String> set = get(local);
		if (null != parameters && !parameters.isEmpty()) {
			for (int i = 0, j = parameters.size(); i < j; i++) {
				identifyAndAdd(set, parameters.get(i));
			} // end for
		} // end if
		if (null != statements && !statements.isEmpty()) {
			for (int i = 0, j = statements.size(); i < j; i++) {
				identifyAndAdd(set, statements.get(i));
			} // end for
		} // end if
		put(clazz, set);
	}// end loadExcludedLocalNames

	/**
	 * identifies the parameter {@link org.eclipse.jdt.core.dom.ASTNode} to examine
	 * for variable names
	 * <p>
	 * logic called recursively to identify nested structures
	 *
	 * @param set  the set of variable names to add to
	 * @param node the {@link org.eclipse.jdt.core.dom.ASTNode} to examine
	 */
	@SuppressWarnings("unchecked")
	private void identifyAndAdd(Set<String> set, ASTNode node) {
		List<Statement> statements = new ArrayList<Statement>(1);
		switch (node.getNodeType()) {
		case BLOCK:
			statements = ((Block) node).statements();
			break;
		case CATCH_CLAUSE:
			if (((CatchClause) node).getBody().getNodeType() == BLOCK) {
				statements = ((CatchClause) node).getBody().statements();
			} else {
				statements.add(((CatchClause) node).getBody());
			} // end if/else
			break;
		case DO_STATEMENT:
			if (((DoStatement) node).getBody().getNodeType() == BLOCK) {
				statements = ((Block) ((DoStatement) node).getBody()).statements();
			} else {
				statements.add(((DoStatement) node).getBody());
			} // end if/else
			break;
		case ENHANCED_FOR_STATEMENT:
			if (((EnhancedForStatement) node).getBody().getNodeType() == BLOCK) {
				statements = ((Block) ((EnhancedForStatement) node).getBody()).statements();
			} else {
				statements.add(((EnhancedForStatement) node).getBody());
			} // end if/else
			break;
		case FOR_STATEMENT:
			if (((ForStatement) node).getBody().getNodeType() == BLOCK) {
				statements = ((Block) ((ForStatement) node).getBody()).statements();
			} else {
				statements.add(((ForStatement) node).getBody());
			} // end if/else
			break;
		case IF_STATEMENT:
			if (((IfStatement) node).getThenStatement().getNodeType() == BLOCK) {
				statements = ((Block) ((IfStatement) node).getThenStatement()).statements();
			} else {
				statements.add(((IfStatement) node).getThenStatement());
			} // end if/else
			for (int i = 0, j = statements.size(); i < j; i++) {
				identifyAndAdd(set, statements.get(i));
			} // end for
			statements = new ArrayList<Statement>(1);
			if (null != ((IfStatement) node).getElseStatement()) {
				if (((IfStatement) node).getElseStatement().getNodeType() == BLOCK) {
					statements = ((Block) ((IfStatement) node).getElseStatement()).statements();
				} else {
					statements.add(((IfStatement) node).getElseStatement());
				} // end if/else
			} // end if
			break;
		case WHILE_STATEMENT:
			if (((WhileStatement) node).getBody().getNodeType() == BLOCK) {
				statements = ((Block) ((WhileStatement) node).getBody()).statements();
			} else {
				statements.add(((WhileStatement) node).getBody());
			} // end if/else
			break;
		case LABELED_STATEMENT:
			if (((LabeledStatement) node).getBody().getNodeType() == BLOCK) {
				statements = ((Block) ((LabeledStatement) node).getBody()).statements();
			} else {
				statements.add(((LabeledStatement) node).getBody());
			} // end if/else
			break;
		case SINGLE_VARIABLE_DECLARATION:
			set.add(((SingleVariableDeclaration) node).getName().toString());
			break;
		case SWITCH_STATEMENT:
			statements = ((SwitchStatement) node).statements();
			break;
		case TRY_STATEMENT:
			statements.addAll(((TryStatement) node).getBody().statements());
			if (null != ((TryStatement) node).catchClauses() && !((TryStatement) node).catchClauses().isEmpty()) {
				List<CatchClause> catchClauses = ((TryStatement) node).catchClauses();
				for (int i = 0, j = catchClauses.size(); i < j; i++) {
					statements.addAll(catchClauses.get(i).getBody().statements());
				} // end for
			} // end if
			if (null != ((TryStatement) node).getFinally()) {
				statements.addAll(((TryStatement) node).getFinally().statements());
			} // end if
			break;
		case VARIABLE_DECLARATION_STATEMENT:
			VariableDeclarationStatement statement = (VariableDeclarationStatement) node;
			VariableDeclarationFragment fragment;
			List<VariableDeclarationFragment> fragments = statement.fragments();
			for (int k = 0, l = fragments.size(); k < l; k++) {
				fragment = fragments.get(k);
				set.add(fragment.getName().toString());
			} // end for
			break;
		default:
			break;
		}// end switch
		if (null != statements && !statements.isEmpty()) {
			for (int i = 0, j = statements.size(); i < j; i++) {
				identifyAndAdd(set, statements.get(i));
			} // end for
		} // end if
	}// end identifyAndAdd

	/**
	 * combines the set of variable names found in both the class and method to
	 * exclude
	 *
	 * @return a {@link java.util.Set} of variable names which already exist in the
	 *         current class and method
	 */
	public Set<String> getExcludedNames() {
		Set<String> set = new HashSet<>();
		Iterator<String> iter = keySet().iterator();
		while (iter.hasNext()) {
			set.addAll(get(iter.next()));
		} // end while
		return set;
	}// end getExcludedNames

	/**
	 * adds the newly created local variable name to the set of local names
	 *
	 * @param name the variable name to add
	 * @return the parameter variable name
	 */
	public String addExcludedLocalName(String name) {
		Set<String> set = get(local);
		set.add(name);
		put(local, set);
		return name;
	}// end addExcludedLocalName

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#clone()
	 */
	@Override
	public ExcludedNamesMap clone() {
		return (ExcludedNamesMap) super.clone();
	}// end clone
}// end class
