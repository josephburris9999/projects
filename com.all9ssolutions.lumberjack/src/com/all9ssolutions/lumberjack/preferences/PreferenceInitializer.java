/**
 * @(#)PreferenceInitializer.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.OFF;
import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.UTIL;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.all9ssolutions.lumberjack.Activator;

/**
 * This class is used to initialize default preference values.
 * 
 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer implements PreferenceConstants {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setDefault(FRAMEWORK_COMBO, UTIL);
		preferences.setDefault(LOGGER_VAR_NAME, "logger");
		preferences.setDefault(ENTER_LEVEL, OFF);
		preferences.setDefault(INCLUDE_PARAMS, OFF);
		preferences.setDefault(WRITE_COMMENT, OFF);
		preferences.setDefault(WRITE_ENTER_IF, OFF);
		preferences.setDefault(EXIT_LEVEL, OFF);
		preferences.setDefault(INCLUDE_RET_VAL, OFF);
		preferences.setDefault(WRITE_EXIT_IF, OFF);
		preferences.setDefault(WRITE_IF_ELSE, OFF);
		preferences.setDefault(WRITE_DO_WHILE, OFF);
		preferences.setDefault(WRITE_WHILE, OFF);
		preferences.setDefault(WRITE_FOR, OFF);
		preferences.setDefault(WRITE_SWITCH, OFF);
		preferences.setDefault(WRITE_TRY_CATCH, OFF);
		preferences.setDefault(WRITE_VARIABLE, OFF);
		preferences.setDefault(WRITE_METHOD_CALL, OFF);
		preferences.setDefault(WRITE_SYNCHRONIZED, OFF);
	}// end initializeDefaultPreferences
}// end class
