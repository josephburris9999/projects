/**
 * @(#)SelectionsPage.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_methods;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_no_methods;
import static com.all9ssolutions.lumberjack.Activator.PLUGIN_ID;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_class_obj;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_description;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_private_method_obj;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_protected_method_obj;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_public_method_obj;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_selections_title;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.all9ssolutions.lumberjack.Activator;

/**
 * This class displays the classes and methods available for selection to
 * generate logging.
 *
 * @see org.eclipse.jface.wizard.WizardPage
 */
public class SelectionsPage extends WizardPage implements SelectionListener {
	private final Object[] empty = new Object[0];
	private Map<TypeDeclaration, List<MethodDeclaration>> methodMap;
	private CheckboxTreeViewer viewer;
	private Button selectButton;
	private Button deselectButton;
	private Button preferencesButton;
	private ContentProvider contentProvider;
	private Validator validator;

	/**
	 * default constructor instantiates this class
	 */
	public SelectionsPage() {
		super("Selections");
		setTitle(LUMBERJACK_selections_title);
		setDescription(LUMBERJACK_selections_description);
		setImageDescriptor(ImageDescriptor.createFromURL(
				SelectionsPage.class.getResource(Activator.getDefault().getResourceBundle().getString("page.image"))));
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		getShell().setText(LUMBERJACK_selections_title);
		methodMap = ((Wizard) getWizard()).getMethodMap();
		validator = new Validator();
		Composite page = new Composite(parent, SWT.NONE);
		setControl(page);
		page.setLayout(new GridLayout(2, false));
		viewer = new CheckboxTreeViewer(page, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(contentProvider = new ContentProvider());
		viewer.setLabelProvider(new LabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object obj) {
				if (obj instanceof TypeDeclaration) {
					return ((TypeDeclaration) obj).getName().toString();
				} // end if
				MethodDeclaration method = (MethodDeclaration) obj;
				String label = method.getName().toString() + "(";
				if (!method.parameters().isEmpty()) {
					SingleVariableDeclaration variable;
					for (int i = 0, j = method.parameters().size(), k = method.parameters().size() - 1; i < j; i++) {
						variable = (SingleVariableDeclaration) method.parameters().get(i);
						label += variable.toString();
						if (i != k) {
							label += ", ";
						} // end if
					} // end for
				} // end if
				label += ")";
				return label;
			}// end getText

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
			 */
			@Override
			public Image getImage(Object element) {
				String imageKey = null;
				if ((element instanceof TypeDeclaration)) {
					imageKey = LUMBERJACK_selections_class_obj;
				} else {
					if (Modifier.isPrivate(((MethodDeclaration) element).getModifiers())) {
						imageKey = LUMBERJACK_selections_private_method_obj;
					} else if (Modifier.isProtected(((MethodDeclaration) element).getModifiers())) {
						imageKey = LUMBERJACK_selections_protected_method_obj;
					} // end if/else
					if (null == imageKey) {
						imageKey = LUMBERJACK_selections_public_method_obj;
					} // end if
				} // end if/else
				return ImageDescriptor.createFromURL(SelectionsPage.class.getResource(imageKey)).createImage();
			}// end getImage
		});
		viewer.addCheckStateListener(checkStateListener);
		viewer.setInput(new Object());
		viewer.expandAll();
		GridData data = new GridData(GridData.FILL_BOTH);
		Tree treeWidget = viewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
		Composite panel = new Composite(page, SWT.NONE);
		panel.setLayout(new GridLayout(1, false));
		selectButton = new Button(panel, 8);
		selectButton.setText("Select All");
		selectButton.setFont(parent.getFont());
		selectButton.addSelectionListener(this);
		setButtonLayoutData(selectButton);
		deselectButton = new Button(panel, 8);
		deselectButton.setText("Deselect All");
		deselectButton.setFont(parent.getFont());
		deselectButton.addSelectionListener(this);
		setButtonLayoutData(deselectButton);
		preferencesButton = new Button(panel, 8);
		preferencesButton.setText("Preferences..");
		preferencesButton.setFont(parent.getFont());
		preferencesButton.addSelectionListener(this);
		setButtonLayoutData(preferencesButton);
		Object cdata = preferencesButton.getLayoutData();
		((GridData) cdata).verticalSpan = 25;
		preferencesButton.setLayoutData(cdata);
	}// end createControl

	private ICheckStateListener checkStateListener = new ICheckStateListener() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.
		 * jface.viewers.CheckStateChangedEvent)
		 */
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			if (event.getElement() instanceof TypeDeclaration) {
				Object[] viewerElements = contentProvider.getChildren(event.getElement());
				for (int i = 0, j = viewerElements.length; i < j; ++i) {
					viewer.setSubtreeChecked(viewerElements[i], event.getChecked());
				} // end for
			} else {
				viewer.setSubtreeChecked(event.getElement(), event.getChecked());
				Object obj = contentProvider.getParent(event.getElement());
				if (empty != obj) {
					int count = 0;
					Object[] array = contentProvider.getChildren(obj);
					for (int i = 0, j = array.length; i < j; i++) {
						if (viewer.getChecked(array[i])) {
							count++;
						} // end if
					} // end for
					viewer.setChecked(obj, (count == array.length));
				} // end if
			} // end if/else
			updateStatus(validator.validate(viewer.getCheckedElements()));
		}// end checkStateChanged
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return validator.validate(viewer.getCheckedElements()).isOK();
	}// end canFlipToNextPage

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		((Wizard) getWizard()).getAction().process();
		return super.getNextPage();
	}// end getNextPage

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		setPageComplete(validator.validate(viewer.getCheckedElements()).isOK());
		super.setVisible(visible);
	}// end setVisible

	/**
	 * updates the message or error message on the wizard based on the validation
	 * status
	 *
	 * @param status the {@code IStatus} containing the message to write on the
	 *               wizard
	 */
	private void updateStatus(IStatus status) {
		if (status.isOK()) {
			setErrorMessage(null);
			setMessage(status.getMessage());
			Activator.getDefault().getMethodMap().set(viewer.getCheckedElements());
		} else {
			setErrorMessage(status.getMessage());
		} // end if/else
		setPageComplete(status.isOK());
		getContainer().updateButtons();
	}// end updateStatus

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.
	 * events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent event) {
		if (event.getSource() == selectButton) {
			for (TypeDeclaration type : methodMap.keySet()) {
				viewer.setSubtreeChecked(type, true);
			} // end for
			updateStatus(validator.validate(viewer.getCheckedElements()));
		} else if (event.getSource() == deselectButton) {
			viewer.setCheckedElements(new Object[0]);
			updateStatus(validator.validate(viewer.getCheckedElements()));
		} else if (event.getSource() == preferencesButton) {
			PreferencesUtil.createPreferenceDialogOn(getShell(),
					"com.all9ssolutions.lumberjack.preferences.PreferencePage", null, null).open();
		} // end if/else
	}// end widgetSelected

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.
	 * swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
	}// end widgetDefaultSelected

	/**
	 * This nested class is used to interpret the methods for selection in a format
	 * compliant with an instance of the
	 * {@link org.eclipse.jface.viewers.CheckboxTreeViewer}.
	 *
	 */
	private class ContentProvider implements ITreeContentProvider {
		private TypeDeclaration[] types;

		/**
		 * default constructor instantiates this class
		 */
		public ContentProvider() {
			types = methodMap.keySet().toArray(new TypeDeclaration[methodMap.keySet().size()]);
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof TypeDeclaration) {
				List<MethodDeclaration> list = methodMap.get(element);
				return list.toArray(new MethodDeclaration[list.size()]);
			} // end if
			return empty;
		}// end getChildren

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		@Override
		public Object getParent(Object element) {
			if (element instanceof MethodDeclaration) {
				TypeDeclaration type;
				for (int i = 0, j = types.length; i < j; i++) {
					type = (TypeDeclaration) ((MethodDeclaration) element).getParent();
					if (types[i] == type) {
						return type;
					} // end if
				} // end for
			} // end if
			return empty;
		}// end getParent

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof TypeDeclaration) && methodMap.get(element).size() > 0;
		}// end hasChildren

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return types;
		}// end getElements

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
		}// end dispose

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.
		 * viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}// end inputChanged
	}// end nested class

	/**
	 * This nested class validates the method selections to generate logging for.
	 *
	 */
	private class Validator implements ISelectionStatusValidator {
		private int methodCnt;

		/**
		 * default constructor instantiates this class
		 */
		public Validator() {
			super();
			for (TypeDeclaration type : methodMap.keySet()) {
				Object obj;
				for (Iterator<?> subiter = type.bodyDeclarations().iterator(); subiter.hasNext();) {
					obj = subiter.next();
					if (obj instanceof MethodDeclaration && !((MethodDeclaration) obj).isConstructor()) {
						methodCnt++;
					} // end if
				} // end for
			} // end for
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
		 */
		@Override
		public IStatus validate(Object[] selection) {
			int count = 0;
			for (int i = 0, j = selection.length; i < j; ++i) {
				if (selection[i] instanceof MethodDeclaration) {
					count++;
				} // end if
			} // end for
			if (count > 0) {
				return new Status(IStatus.OK, PLUGIN_ID, MessageFormat.format(LUMBERJACK_selections_methods,
						new Object[] { String.valueOf(count), String.valueOf(methodCnt) }), null);
			} else {
				return new Status(IStatus.ERROR, PLUGIN_ID, LUMBERJACK_selections_no_methods, null);
			} // end if/else
		}// end validate
	}// end nested class
}// end class
