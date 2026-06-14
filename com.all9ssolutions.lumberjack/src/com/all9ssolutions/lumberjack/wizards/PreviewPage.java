/**
 * @(#)PreviewPage.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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

import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_preview_compilation_unit;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_preview_description;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_preview_icon;
import static com.all9ssolutions.lumberjack.Messages.LUMBERJACK_preview_title;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DocumentRangeNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.all9ssolutions.lumberjack.Activator;
import com.all9ssolutions.lumberjack.business.Operation;

/**
 * This class displays a side-by-side preview of the Java class in the current
 * editor with the left side displaying the original version and the right side
 * displaying the code with generated logging statements added.
 *
 * @see org.eclipse.jface.wizard.WizardPage
 */
public class PreviewPage extends WizardPage {
	private static final Image image = ImageDescriptor
			.createFromURL(PreviewPage.class.getResource(LUMBERJACK_preview_icon)).createImage();
	private ContentViewer viewer;

	/**
	 * default constructor instantiates this class
	 */
	public PreviewPage() {
		super("Preview");
		setTitle(LUMBERJACK_preview_title);
		setDescription(LUMBERJACK_preview_description);
		setImageDescriptor(ImageDescriptor.createFromURL(
				PreviewPage.class.getResource(Activator.getDefault().getResourceBundle().getString("page.image"))));
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
		getShell().setText(LUMBERJACK_preview_title);
		Composite page = new Composite(parent, SWT.NONE);
		setControl(page);
		setPageComplete(true);
		page.setLayout(new FillLayout());
		viewer = new ContentViewer(page, new CompareConfiguration());
	}// end createControl

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			try {
				JavaNode[] elements = new JavaNode[2];
				CompilationUnitChange change = Operation.createChange();
				elements[0] = new JavaNode(change.getCurrentDocument(null));
				elements[1] = new JavaNode(change.getPreviewDocument(null));
				viewer.getContentProvider().inputChanged(viewer, elements,
						Activator.getDefault().getQualifiedClassPath());
				viewer.refresh();
			} catch (CoreException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"Unable to prepare the Lumberjack preview.", e), StatusManager.LOG | StatusManager.SHOW);
			} // end try/catch
		} // end if
		super.setVisible(visible);
	}// end setVisible

	/**
	 * This class is used to construct and display the compare editor in the
	 * {@link org.eclipse.jface.wizard.WizardPage}.
	 * <p>
	 * This class is sub-classed for future enhancements.
	 *
	 * @see org.eclipse.compare.contentmergeviewer.TextMergeViewer
	 */
	private class ContentViewer extends TextMergeViewer {

		/**
		 * overloaded constructor instantiates this class
		 *
		 * @param parent the {@link org.eclipse.swt.widgets.Composite}
		 * @param config the configuration used to display text in the comparator editor
		 */
		public ContentViewer(Composite parent, CompareConfiguration config) {
			super(parent, SWT.NONE | 0x2000000, config);
			setContentProvider(new ContentProvider(config));
		}// end constructor
	}// end nested class

	/**
	 * This nested class is used to provide the content for the compare editor.
	 *
	 */
	private class ContentProvider implements IMergeViewerContentProvider {
		private CompareConfiguration config;
		private JavaNode[] elements;

		/**
		 * overloaded constructor instantiates this class
		 *
		 * @param config the compare configuration for displaying content
		 */
		public ContentProvider(CompareConfiguration config) {
			super();
			this.config = config;
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.
		 * viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object elements, Object name) {
			if (null != elements) {
				this.elements = (JavaNode[]) elements;
				config.setLeftLabel(String.valueOf(name));
				config.setLeftImage(image);
				config.setRightLabel(String.valueOf(name));
				config.setRightImage(image);
			} // end if
		}// end inputChanged

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getAncestorLabel(java.lang.Object)
		 */
		@Override
		public String getAncestorLabel(Object element) {
			return null;
		}// end getAncestorLabel

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getAncestorImage(java.lang.Object)
		 */
		@Override
		public Image getAncestorImage(Object element) {
			return null;
		}// end getAncestorImage

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getAncestorContent(java.lang.Object)
		 */
		@Override
		public Object getAncestorContent(Object element) {
			return null;
		}// end getAncestorContent

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * showAncestor(java.lang.Object)
		 */
		@Override
		public boolean showAncestor(Object element) {
			return (element instanceof ICompareInput);
		}// end showAncestor

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getLeftLabel(java.lang.Object)
		 */
		@Override
		public String getLeftLabel(Object element) {
			return config.getLeftLabel(element);
		}// end getLeftLabel

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getLeftImage(java.lang.Object)
		 */
		@Override
		public Image getLeftImage(Object element) {
			return config.getLeftImage(element);
		}// end getLeftImage

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getLeftContent(java.lang.Object)
		 */
		@Override
		public Object getLeftContent(Object element) {
			if (null != elements) {
				return elements[0];
			} // end if
			return null;
		}// end getLeftContent

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * isLeftEditable(java.lang.Object)
		 */
		@Override
		public boolean isLeftEditable(Object element) {
			if (element instanceof ICompareInput) {
				Object left = ((ICompareInput) element).getLeft();
				if ((left == null) && (element instanceof IDiffElement)) {
					IDiffElement parent = ((IDiffElement) element).getParent();
					if (parent instanceof ICompareInput) {
						left = ((ICompareInput) parent).getLeft();
					} // end if
				} // end if
					// if(left instanceof IEditableContent){
					// return ((IEditableContent) left).isEditable();
					// }// end if
			} // end if
			return false;
		}// end isLeftEditable

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * saveLeftContent(java.lang.Object, byte[])
		 */
		@Override
		public void saveLeftContent(Object element, byte[] bytes) {
			if (element instanceof ICompareInput) {
				ICompareInput node = (ICompareInput) element;
				if (bytes != null) {
					ITypedElement left = node.getLeft();
					if (left == null) {
						node.copy(false);
						left = node.getLeft();
					} // end if
					if (left instanceof IEditableContent) {
						((IEditableContent) left).setContent(bytes);
					} // end if
				} else {
					node.copy(false);
				} // end if/else
			} // end if
		}// end saveLeftContent

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getRightLabel(java.lang.Object)
		 */
		@Override
		public String getRightLabel(Object element) {
			return config.getRightLabel(element);
		}// end getRightLabel

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getRightImage(java.lang.Object)
		 */
		@Override
		public Image getRightImage(Object element) {
			return config.getRightImage(element);
		}// end getRightImage

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * getRightContent(java.lang.Object)
		 */
		@Override
		public Object getRightContent(Object element) {
			if (null != elements) {
				return elements[1];
			} // end if
			return null;
		}// end getRightContent

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * isRightEditable(java.lang.Object)
		 */
		@Override
		public boolean isRightEditable(Object element) {
			if (element instanceof ICompareInput) {
				Object right = ((ICompareInput) element).getRight();
				if ((right == null) && (element instanceof IDiffElement)) {
					IDiffContainer parent = ((IDiffElement) element).getParent();
					if (parent instanceof ICompareInput) {
						right = ((ICompareInput) parent).getRight();
					} // end if
				} // end if
					// if(right instanceof IEditableContent){
					// return ((IEditableContent) right).isEditable();
					// }// end if
			} // end if
			return false;
		}// end isRightEditable

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider#
		 * saveRightContent(java.lang.Object, byte[])
		 */
		@Override
		public void saveRightContent(Object element, byte[] bytes) {
			if (element instanceof ICompareInput) {
				ICompareInput node = (ICompareInput) element;
				if (bytes != null) {
					ITypedElement right = node.getRight();
					if (right == null) {
						node.copy(true);
						right = node.getRight();
					} // end if
					if (right instanceof IEditableContent) {
						((IEditableContent) right).setContent(bytes);
					} // end if
				} else {
					node.copy(true);
				} // end if/else
			} // end if
		}// end saveRightContent

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
		}// end dispose
	}// end nested class

	/**
	 * This nested class is used to wrap an instance of
	 * {@link org.eclipse.jface.text.IDocument} in an instance of
	 * {@link org.eclipse.compare.ITypedElement}.
	 *
	 * @see org.eclipse.compare.structuremergeviewer.DocumentRangeNode
	 */
	private class JavaNode extends DocumentRangeNode implements ITypedElement {

		/**
		 * overloaded constructor instantiates this class
		 *
		 * @param document the {@link org.eclipse.jface.text.IDocument} to create the
		 *                 node
		 */
		public JavaNode(IDocument document) {
			super(0, "root{", document, 0, document.getLength());
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.ITypedElement#getName()
		 */
		@Override
		public String getName() {
			return LUMBERJACK_preview_compilation_unit;
		}// end getName

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.ITypedElement#getType()
		 */
		@Override
		public String getType() {
			return "java2";
		}// end getType

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.compare.ITypedElement#getImage()
		 */
		@Override
		public Image getImage() {
			return image;
		}// end getImage
	}// end nested class
}// end class
