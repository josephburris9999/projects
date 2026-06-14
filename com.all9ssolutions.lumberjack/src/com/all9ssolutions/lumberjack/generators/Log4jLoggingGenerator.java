/**
 * @(#)Log4jLoggingGenerator.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

import com.all9ssolutions.lumberjack.business.Operation;

/**
 * This class is used to model the Log4j logging framework for generating
 * logging statements in the Lumberjack plug-in.
 * 
 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator
 */
public class Log4jLoggingGenerator extends AbstractLoggingGenerator {
	private static final String PRIORITY = "org.apache.log4j.Priority";

	/**
	 * default constructor instantiates this class
	 */
	public Log4jLoggingGenerator() {
		super();
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * getLoggingDataTypeClassName()
	 */
	@Override
	public String getLoggingDataTypeClassName() {
		return "org.apache.log4j.Logger";
	}// end getLoggingDataTypeClassName

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * getLoggingLevels ()
	 */
	@Override
	public String[][] getLoggingLevels() {
		return new String[][] { //
				{ OFF, OFF }, //
				{ "DEBUG", "DEBUG" }, //
				{ "INFO", "INFO" }, //
				{ "WARN", "WARN" }, //
				{ "ERROR", "ERROR" }, //
				{ "FATAL", "FATAL" }, //
		};
	}// end getLoggingLevels

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * getLoggingInstantiationMethod()
	 */
	@Override
	public String getLoggingInstantiationMethod() {
		return "getLogger";
	}// end getLoggingInstantiationMethod

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * getIsLoggableMethod(java.lang.String)
	 */
	@Override
	public String getIsLoggableMethod(String level) {
		return "isEnabledFor";
	}// end getIsLoggableMethod

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * dowWriteEnterIf(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	protected IfStatement doWriteEnterIf(MethodDeclaration method) {
		IfStatement statement = null;
		if (preferences.getBoolean(WRITE_ENTER_IF)
				&& (preferences.getBoolean(WRITE_COMMENT) || preferences.getBoolean(INCLUDE_PARAMS))) {
			AST ast = method.getAST();
			String isMethod = getIsLoggableMethod(preferences.getString(ENTER_LEVEL));
			statement = ast.newIfStatement();
			QualifiedName qname = ast.newQualifiedName(ast.newName(Operation.getImportRewrite().addImport(PRIORITY)),
					ast.newSimpleName(preferences.getString(ENTER_LEVEL)));
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), isMethod,
					qname);
			statement.setExpression(invocation);
		} // end if
		return statement;
	}// end doWriteEnterIf

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator#
	 * dowWriteExitIf(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	protected IfStatement doWriteExitIf(MethodDeclaration method) {
		IfStatement statement = null;
		if (preferences.getBoolean(WRITE_EXIT_IF) && preferences.getBoolean(INCLUDE_RET_VAL) && !isVoidReturn(method)) {
			AST ast = method.getAST();
			String isMethod = getIsLoggableMethod(preferences.getString(EXIT_LEVEL));
			statement = ast.newIfStatement();
			QualifiedName qname = ast.newQualifiedName(ast.newName(Operation.getImportRewrite().addImport(PRIORITY)),
					ast.newSimpleName(preferences.getString(EXIT_LEVEL)));
			MethodInvocation invocation = createMethodInvocation(ast, preferences.getString(LOGGER_VAR_NAME), isMethod,
					qname);
			statement.setExpression(invocation);
		} // end if
		return statement;
	}// end doWriteExitIf
}// end class
