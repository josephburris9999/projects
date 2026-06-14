/**
 * @(#)PreferencePage.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.LOGGING_FRAMEWORKS;
import static com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator.OFF;
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.all9ssolutions.lumberjack.Activator;
import com.all9ssolutions.lumberjack.business.Operation.LoggingGeneratorFactory;
import com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator;

/**
 * This class represents the Lumberjack preference page which contributes to the
 * Window &rarr; General &rarr; Preferences dialog. By sub-classing
 * {@code FieldEditorPreferencePage}, field support built into JFace is provided
 * allowing creation of a page that is small and knows how to save, restore and
 * apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 * </p>
 * 
 * @see org.eclipse.jface.preference.FieldEditorPreferencePage
 */
public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage, PreferenceConstants {
	private ComboFieldEditor frameworkControl;
	private ComboFieldEditor enterControl;
	private ComboFieldEditor exitControl;
	private ComboFieldEditor ifElseControl;
	private ComboFieldEditor doWhileControl;
	private ComboFieldEditor whileControl;
	private ComboFieldEditor forControl;
	private ComboFieldEditor tryCatchControl;
	private ComboFieldEditor switchControl;
	private ComboFieldEditor variableControl;
	private ComboFieldEditor methodControl;
	private ComboFieldEditor synchronizedControl;

	/**
	 * default constructor instantiates this class
	 */
	public PreferencePage() {
		super(GRID);
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		setTitle(bundle.getString("preferences.page.title"));
		setDescription(bundle.getString("preferences.page.description"));
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}// end init

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	public void createFieldEditors() {
		addField(new StringFieldEditor(LOGGER_VAR_NAME, "preferences.logger_field.label",
				"preferences.logger_field.tool_tip", "preferences.logger_field.error_message", getFieldEditorParent()));

		addField(frameworkControl = new ComboFieldEditor(FRAMEWORK_COMBO, "preferences.label.framework",
				"preferences.tool_tip.framework", LOGGING_FRAMEWORKS, getFieldEditorParent()));

		String[][] loggingLevels = getLoggingLevels(getPreferenceStore().getString(FRAMEWORK_COMBO));

		addField(enterControl = new ComboFieldEditor(ENTER_LEVEL, "preferences.label.write_enter",
				"preferences.tool_tip.enter_level", loggingLevels, getFieldEditorParent()));

		BooleanFieldEditor includeParams = new BooleanFieldEditor(INCLUDE_PARAMS, "preferences.label.include_params",
				getFieldEditorParent());
		addField(includeParams);

		BooleanFieldEditor writeComment = new BooleanFieldEditor(WRITE_COMMENT, "preferences.label.write_comment",
				getFieldEditorParent());
		addField(writeComment);

		BooleanFieldEditor writeEnterIf = new BooleanFieldEditor(WRITE_ENTER_IF, "preferences.label.write_enter_if",
				getFieldEditorParent());
		addField(writeEnterIf);

		enterControl.setCheckBoxes(new BooleanFieldEditor[] { includeParams, writeComment, writeEnterIf },
				getFieldEditorParent());

		addField(exitControl = new ComboFieldEditor(EXIT_LEVEL, "preferences.label.write_exit",
				"preferences.tool_tip.exit_level", loggingLevels, getFieldEditorParent()));

		BooleanFieldEditor includeRetVal = new BooleanFieldEditor(INCLUDE_RET_VAL, "preferences.label.include_ret_val",
				getFieldEditorParent());
		addField(includeRetVal);

		BooleanFieldEditor writeExitIf = new BooleanFieldEditor(WRITE_EXIT_IF, "preferences.label.write_exit_if",
				getFieldEditorParent());
		addField(writeExitIf);

		exitControl.setCheckBoxes(new BooleanFieldEditor[] { includeRetVal, writeExitIf }, getFieldEditorParent());

		addField(ifElseControl = new ComboFieldEditor(WRITE_IF_ELSE, "preferences.label.write_if_else",
				"preferences.tool_tip.write_if_else", loggingLevels, getFieldEditorParent()));

		addField(doWhileControl = new ComboFieldEditor(WRITE_DO_WHILE, "preferences.label.write_do_while",
				"preferences.tool_tip.write_do_while", loggingLevels, getFieldEditorParent()));

		addField(whileControl = new ComboFieldEditor(WRITE_WHILE, "preferences.label.write_while",
				"preferences.tool_tip.write_while", loggingLevels, getFieldEditorParent()));

		addField(forControl = new ComboFieldEditor(WRITE_FOR, "preferences.label.write_for",
				"preferences.tool_tip.write_for", loggingLevels, getFieldEditorParent()));

		addField(tryCatchControl = new ComboFieldEditor(WRITE_TRY_CATCH, "preferences.label.write_try_catch",
				"preferences.tool_tip.write_try_catch", loggingLevels, getFieldEditorParent()));

		addField(synchronizedControl = new ComboFieldEditor(WRITE_SYNCHRONIZED, "preferences.label.write_synchronized",
				"preferences.tool_tip.write_synchronized", loggingLevels, getFieldEditorParent()));

		addField(variableControl = new ComboFieldEditor(WRITE_VARIABLE, "preferences.label.write_variable",
				"preferences.tool_tip.write_variable", loggingLevels, getFieldEditorParent()));

		addField(methodControl = new ComboFieldEditor(WRITE_METHOD_CALL, "preferences.label.write_method_call",
				"preferences.tool_tip.write_method_call", loggingLevels, getFieldEditorParent()));

		addField(switchControl = new ComboFieldEditor(WRITE_SWITCH, "preferences.label.write_switch",
				"preferences.tool_tip.write_switch", loggingLevels, getFieldEditorParent()));

		noDefaultAndApplyButton();
	}// end createFieldEditors

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.
	 * eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() == frameworkControl) {
			String[][] loggingLevels = getLoggingLevels((String) event.getNewValue());
			enterControl.reload(loggingLevels, getFieldEditorParent());
			exitControl.reload(loggingLevels, getFieldEditorParent());
			ifElseControl.reload(loggingLevels, getFieldEditorParent());
			doWhileControl.reload(loggingLevels, getFieldEditorParent());
			whileControl.reload(loggingLevels, getFieldEditorParent());
			forControl.reload(loggingLevels, getFieldEditorParent());
			tryCatchControl.reload(loggingLevels, getFieldEditorParent());
			switchControl.reload(loggingLevels, getFieldEditorParent());
			variableControl.reload(loggingLevels, getFieldEditorParent());
			methodControl.reload(loggingLevels, getFieldEditorParent());
			synchronizedControl.reload(loggingLevels, getFieldEditorParent());
		} // end if
	}// end propertyChange

	/**
	 * gets the logging levels from the
	 * {@link com.all9ssolutions.lumberjack.generators.AbstractLoggingGenerator}
	 * sub-class for the parameter framework
	 * 
	 * @param framework the backing value of the client selected logging framework
	 * @return a {@code String[][]} of logging levels to display
	 */
	private String[][] getLoggingLevels(String framework) {
		AbstractLoggingGenerator generator = LoggingGeneratorFactory.getLoggingGenerator(framework);
		return ((AbstractLoggingGenerator) generator).getLoggingLevels();
	}// end getLoggingLevels

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), //
				"com.all9ssolutions.lumberjack.help.lumberjack_context");
	}// end createControl
}// end class

/**
 * A field editor for a string type preference.
 * 
 * @see org.eclipse.jface.preference.StringFieldEditor
 */
class StringFieldEditor extends org.eclipse.jface.preference.StringFieldEditor {

	/**
	 * creates a string field editor
	 *
	 * @param name            the name of the preference this field editor works on
	 * @param labelKey        the label property key of the field editor
	 * @param toolTipKey      the tool tip property key of the field editor
	 * @param errorMessageKey the error message property key of the field editor
	 * @param parent          the parent of the field editor's control
	 */
	public StringFieldEditor(String name, String labelKey, String toolTipKey, String errorMessageKey,
			Composite parent) {
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		init(name, bundle.getString(labelKey));
		createControl(parent);
		setTextLimit(20);
		setValidateStrategy(VALIDATE_ON_KEY_STROKE);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		getLabelControl(parent).setToolTipText(bundle.getString(toolTipKey));
		setErrorMessage(MessageFormat.format(bundle.getString(errorMessageKey), getLabelText()));
		load();
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.StringFieldEditor#doCheckState()
	 */
	@Override
	protected boolean doCheckState() {
		boolean result = false;
		if (null != getStringValue() && !"".equals(getStringValue())) {
			char[] chars = getStringValue().toCharArray();
			test: if (Character.isJavaIdentifierStart(chars[0])) {
				for (int i = 1, j = chars.length; i < j; i++) {
					if (!Character.isJavaIdentifierPart(chars[i])) {
						result = false;
						break test;
					} // end if
				} // end for
				result = true;
			} else {
				result = false;
			} // end if/else
		} // end if
		return result;
	}// end doCheckState
}// end class

/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: IBM Corporation - initial API and implementation Remy Chi Jian
 * Suen <remy.suen@gmail.com> - Bug 214392 missing implementation of
 * ComboFieldEditor.setEnabled Pierre-Yves B. <pyvesdev@gmail.com> - Bug 497619
 * - ComboFieldEditor doesnt fire PropertyChangeEvent for doLoadDefault and
 * doLoad
 *******************************************************************************/
//package org.eclipse.jface.preference;

/**
 * A field editor for a combo box that allows the drop-down selection of one of
 * a list of items.
 *
 * @since 3.3
 * @see org.eclipse.jface.preference.FieldEditor
 */
class ComboFieldEditor extends org.eclipse.jface.preference.FieldEditor {

	/** the {@code Combo} widget */
	private Combo combo;
	/**
	 * the value (not the name) of the currently selected item in the {@code Combo}
	 * widget
	 */
	private String value;
	/** the names (labels) and underlying values to populate the combo widget */
	private String[][] entryNamesAndValues;
	/** the associated {@code BooleanFieldEditor}s for the component */
	private BooleanFieldEditor[] checkBoxes;

	/**
	 * creates the combo box field editor
	 *
	 * @param name                the name of the preference this field editor works
	 *                            on
	 * @param labelKey            the label property key of the field editor
	 * @param toolTipKey          the tool tip property key of the field editor
	 * @param entryNamesAndValues the names (labels) and underlying values to
	 *                            populate the combo widget. These should be
	 *                            arranged as: { {name1, value1}, {name2, value2},
	 *                            ...}
	 * @param parent              the parent of the field editor's control
	 */
	public ComboFieldEditor(String name, String labelKey, String toolTipKey, String[][] entryNamesAndValues,
			Composite parent) {
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		init(name, bundle.getString(labelKey));
		this.entryNamesAndValues = entryNamesAndValues;
		createControl(parent);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		getLabelControl(parent).setToolTipText(bundle.getString(toolTipKey));
		load();
	}// end constructor

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	@Override
	protected void adjustForNumColumns(int numColumns) {
		Control control = getLabelControl();
		int left = numColumns;
		if (null != control) {
			((GridData) control.getLayoutData()).horizontalSpan = 1;
			left = left - 1;
		} // end if
		((GridData) combo.getLayoutData()).horizontalSpan = left;
	}// end adjustForNumColumns

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.
	 * widgets.Composite, int)
	 */
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		control.setLayoutData(gd);
		control = getComboBoxControl(parent);
		gd = new GridData();
		gd.horizontalSpan = numColumns - 1;
//		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());
	}// end doFillIntoGrid

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		updateComboForValue(getPreferenceStore().getString(getPreferenceName()));
	}// end doLoad

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	@Override
	protected void doLoadDefault() {
		String oldValue = value;
		updateComboForValue(getPreferenceStore().getDefaultString(getPreferenceName()));
		valueChanged(oldValue, value);
	}// end doLoadDefault

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	@Override
	protected void doStore() {
		if (null == value) {
			getPreferenceStore().setToDefault(getPreferenceName());
			return;
		} // end if
		getPreferenceStore().setValue(getPreferenceName(), value);
	}// end doStore

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	@Override
	public int getNumberOfControls() {
		return 2;
	}// end getNumberOfControls

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ComboFieldEditor#getComboBoxControl(
	 * org.eclipse.swt.widgets.Composite)
	 */
	private Combo getComboBoxControl(Composite parent) {
		if (null == combo) {
			combo = new Combo(parent, SWT.READ_ONLY);
			combo.setFont(parent.getFont());
			for (int i = 0; i < entryNamesAndValues.length; i++) {
				combo.add(entryNamesAndValues[i][0], i);
			} // end for
			combo.select(0);
			combo.addSelectionListener(widgetSelectedAdapter(evt -> {
				String oldValue = value;
				String name = combo.getText();
				value = getValueForName(name);
				setPresentsDefaultValue(false);
				valueChanged(oldValue, value);
				if (null != checkBoxes && checkBoxes.length > 0) {
					for (BooleanFieldEditor checkBox : checkBoxes) {
						if (OFF.equals(value)) {
							checkBox.setEnabled(false, parent);
							checkBox.setSelected(false, parent);
						} else {
							checkBox.setEnabled(true, parent);
						} // end if/else
					} // end for
				} // end if
			}));
		} // end if
		return combo;
	}// end getComboBoxControl

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ComboFieldEditor#getValueForName(java.lang.
	 * String)
	 */
	private String getValueForName(String name) {
		for (String[] entry : entryNamesAndValues) {
			if (name.equals(entry[0])) {
				return entry[1];
			} // end if
		} // end for
		return entryNamesAndValues[0][0];
	}// end getValueForName

	/*
	 * {@non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.ComboFieldEditor#updateComboForValue(java.lang.
	 * String)
	 */
	private void updateComboForValue(String value) {
		this.value = value;
		for (String[] fEntryNamesAndValue : entryNamesAndValues) {
			if (value.equals(fEntryNamesAndValue[1])) {
				combo.setText(fEntryNamesAndValue[0]);
				return;
			} // end if
		} // end for
		if (entryNamesAndValues.length > 0) {
			this.value = entryNamesAndValues[0][1];
			combo.setText(entryNamesAndValues[0][0]);
		} // end if
	}// end updateComboForValue

	/*
	 * {@non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.ComboFieldEditor#valueChanged(java.lang.String,
	 * java.lang.String)
	 */
	protected void valueChanged(String oldValue, String newValue) {
		// Only fire event if old and new values are different.
		if (null != oldValue && !oldValue.equals(newValue) || null != newValue) {
			fireValueChanged(VALUE, oldValue, newValue);
		} // end if
	}// end valueChanged

	/*
	 * {@non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean,
	 * org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		getComboBoxControl(parent).setEnabled(enabled);
	}// end setEnabled

	/**
	 * reloads the combo field editor with different entry names and values
	 * 
	 * @param entryNamesAndValues the names (labels) and underlying values to
	 *                            populate the combo widget. These should be
	 *                            arranged as: { {name1, value1}, {name2, value2},
	 *                            ...}
	 * @param parent              the parent of the field editor's control
	 */
	public void reload(String[][] entryNamesAndValues, Composite parent) {
		this.entryNamesAndValues = entryNamesAndValues;
		combo.removeAll();
		for (int i = 0; i < entryNamesAndValues.length; i++) {
			combo.add(entryNamesAndValues[i][0], i);
		} // end for
		doLoadDefault();
		combo.select(0);
		if (null != checkBoxes && checkBoxes.length > 0) {
			for (BooleanFieldEditor checkBox : checkBoxes) {
				checkBox.setEnabled(false, parent);
				checkBox.setSelected(false, parent);
			} // end for
		} // end if
	}// end reload

	/**
	 * set check boxes as associated components to the combo field editor
	 * 
	 * @param checkBoxes the {@code BooleanFieldEditor[]} to associate
	 * @param parent     the parent of the field editor's control
	 */
	public void setCheckBoxes(BooleanFieldEditor[] checkBoxes, Composite parent) {
		this.checkBoxes = checkBoxes;
		if (null != checkBoxes && checkBoxes.length > 0) {
			for (BooleanFieldEditor checkBox : checkBoxes) {
				if (OFF.equals(value)) {
					checkBox.setEnabled(false, parent);
					checkBox.setSelected(false, parent);
				} else {
					checkBox.setEnabled(true, parent);
				} // end if/else
			} // end for
		} // end if
	}// end setCheckBoxes
}// end class

/**
 * A field editor for a boolean type preference.
 * 
 * @see org.eclipse.jface.preference.BooleanFieldEditor
 */
class BooleanFieldEditor extends org.eclipse.jface.preference.BooleanFieldEditor {

	/**
	 * creates a {@code boolean} field editor
	 *
	 * @param name     the name of the preference this field editor works on
	 * @param labelKey the label property key of the field editor
	 * @param parent   the parent of the field editor's control
	 */
	public BooleanFieldEditor(String name, String labelText, Composite parent) {
		init(name, Activator.getDefault().getResourceBundle().getString(labelText));
		createControl(parent);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		load();
	}// end constructor

	/**
	 * selects or de-selects the check box component
	 * 
	 * @param selected {@code true} if the check box should be selected
	 * @param parent   the parent of the field editor's control
	 */
	public void setSelected(boolean selected, Composite parent) {
		getChangeControl(parent).setSelection(selected);
	}// end setSelected
}// end class