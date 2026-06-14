/**
 * @(#)PreferenceConstants.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.lumberjack.preferences;

/**
 * This interface contains constants for the Lumberjack plug-in preferences.
 * 
 */
public interface PreferenceConstants {

	/**
	 * property key for the framework stored value
	 */
	public static final String FRAMEWORK_COMBO = "Lumberjack.Framework_Combo";

	/**
	 * property key for the logger variable name stored value
	 */
	public static final String LOGGER_VAR_NAME = "Lumberjack.Logger_Var_Name";

	/**
	 * property key for the enter method logging level stored value
	 */
	public static final String ENTER_LEVEL = "Lumberjack.Enter_Level";

	/**
	 * property key for the include parameters {@code boolean} stored value
	 */
	public static final String INCLUDE_PARAMS = "Lumberjack.Include_Params";

	/**
	 * property key for the write comment {@code boolean} stored value
	 */
	public static final String WRITE_COMMENT = "Lumberjack.Write_Comment";

	/**
	 * property key for the write enter if {@code boolean} stored value
	 */
	public static final String WRITE_ENTER_IF = "Lumberjack.Write_Enter_If";

	/**
	 * property key for the exit method logging level stored value
	 */
	public static final String EXIT_LEVEL = "Lumberjack.Exit_Level";

	/**
	 * property key for the include return {@code boolean} stored value
	 */
	public static final String INCLUDE_RET_VAL = "Lumberjack.Include_Ret_Val";

	/**
	 * property key for the write exit if {@code boolean} stored value
	 */
	public static final String WRITE_EXIT_IF = "Lumberjack.Write_Exit_If";

	/**
	 * property key for the {@code if/else} logging level stored value
	 */
	public static final String WRITE_IF_ELSE = "Lumberjack.Write_If_Else";

	/**
	 * property key for the {@code do/while} loop logging level stored value
	 */
	public static final String WRITE_DO_WHILE = "Lumberjack.Write_Do_While";

	/**
	 * property key for the {@code while} loop logging level stored value
	 */
	public static final String WRITE_WHILE = "Lumberjack.Write_While";

	/**
	 * property key for the {@code for} loop logging level stored value
	 */
	public static final String WRITE_FOR = "Lumberjack.Write_For";

	/**
	 * property key for the {@code try/catch/finally} logging level stored value
	 */
	public static final String WRITE_TRY_CATCH = "Lumberjack.Write_Try_Catch";

	/**
	 * property key for the {@code switch} logging level stored value
	 */
	public static final String WRITE_SWITCH = "Lumberjack.Write_Switch";

	/**
	 * property key for the variable logging level stored value
	 */
	public static final String WRITE_VARIABLE = "Lumberjack.Write_Variable";

	/**
	 * property key for the method call logging level stored value
	 */
	public static final String WRITE_METHOD_CALL = "Lumberjack.Write_Method_Call";

	/**
	 * property key for the {@code synchronized} logging level stored value
	 */
	public static final String WRITE_SYNCHRONIZED = "Lumberjack.Write_Synchronized";
}// end interface
