/**
 * @(#)Wizard.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.lumberjack.wizards;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.all9ssolutions.lumberjack.actions.Action;

/**
 * This class loads pages and information for the client using the Lumberjack
 * plug-in.
 *
 * @see org.eclipse.jface.wizard.Wizard
 */
public class Wizard extends org.eclipse.jface.wizard.Wizard {
	private SelectionsPage selections;
	private PreviewPage preview;
	private final Map<TypeDeclaration, List<MethodDeclaration>> methodMap;
	private final Action action;

	/**
	 * default constructor instantiates this class
	 * 
	 * @param methodMap the map of classes and methods as determined by the Java
	 *                  class in the current editor
	 * @param action    the calling instance of
	 *                  {@link com.all9ssolutions.lumberjack.actions.Action}
	 */
	public Wizard(Map<TypeDeclaration, List<MethodDeclaration>> methodMap, Action action) {
		super();
		this.methodMap = methodMap;
		this.action = action;
		setNeedsProgressMonitor(true);
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		selections = new SelectionsPage();
		addPage(selections);
		preview = new PreviewPage();
		addPage(preview);
		Shell shell = getShell();
		if (null != shell) {
			shell.setSize(800, 600);
			Point p = shell.getLocation();
			shell.setLocation(p.x, p.y);
		} // end if
	}// end addPages

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}// end performFinish

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	public void createPageControls(Composite container) {
		super.createPageControls(container);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, //
				"com.all9ssolutions.lumberjack.help.lumberjack_context");
	}// end createPageControls

	/**
	 * returns the available classes and methods found in the Java class in the
	 * current editor
	 * 
	 * @return the map of classes and methods found in the Java class in the current
	 *         editor
	 */
	public Map<TypeDeclaration, List<MethodDeclaration>> getMethodMap() {
		return methodMap;
	}// end getMethodMap

	/**
	 * returns the calling instance of the action
	 * 
	 * @return the calling instance of
	 *         {@link com.all9ssolutions.lumberjack.actions.Action}
	 */
	public Action getAction() {
		return action;
	}// end getAction
}// end class
