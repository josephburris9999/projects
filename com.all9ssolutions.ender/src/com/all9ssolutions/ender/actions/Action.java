/**
 * @(#)Action.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.ender.actions;

import static com.all9ssolutions.ender.Activator.PLUGIN_ID;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_cannot_perform;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_editor;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_fix_problems;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_not_applicable;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_not_java_project;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_not_on_build_path;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_not_on_class_path;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_action_out_of_sync;
import static com.all9ssolutions.ender.Messages.ENDER_error_msg_file_modified;
import static com.all9ssolutions.ender.Messages.ENDER_error_title;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.IThreadListener;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import com.all9ssolutions.ender.business.Operation;

/**
 * This class is the primary action called by the
 * {@link com.all9ssolutions.ender.handlers.Handler}.
 * 
 * @see org.eclipse.jface.action.Action
 */
public class Action extends org.eclipse.jface.action.Action {
	private IEditorPart editor;
	private IWorkbenchSite site;
	private Shell shell;
	private IType type;
	private Operation operation;

	/**
	 * overloaded constructor instantiates this class
	 *
	 * @param window the current workbench window
	 */
	public Action(IWorkbenchWindow window) {
		super();
		Assert.isNotNull(window);
		IWorkbenchPage page = window.getActivePage();
		Assert.isNotNull(page.getActiveEditor());
		editor = page.getActiveEditor();
		Assert.isNotNull(editor.getSite());
		site = page.getActiveEditor().getSite();
		shell = site.getShell();
		setEnabled(true);
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		boolean dirty = editor.isDirty();
		if (dirty) {
			MessageDialog.openInformation(shell, ENDER_error_title, ENDER_error_msg_action_editor);
			notifyResult(false);
			return;
		} // end if
		int result = -1;
		boolean flag = false;
		try {
			type = getTypeAtOffset();
			if (null == type) {
				notifyResult(flag);
				throw new CoreException(
						new Status(Status.INFO, PLUGIN_ID, ENDER_error_msg_action_not_applicable, null));
			} // end if
			if ((!(isAlterable(type))) || (!(isEditable(type)))) {
				notifyResult(flag);
			} // end if
			if (type.isAnnotation() || type.isInterface() || type.isEnum() || type.isAnonymous() || type.isRecord()) {
				notifyResult(flag);
				throw new CoreException(
						new Status(IStatus.INFO, PLUGIN_ID, ENDER_error_msg_action_not_applicable, null));
			} // end if
			operation = new Operation(type);
			CompilationUnit unit = operation.getUnit();
			if (unit.getProblems().length > 0) {
				notifyResult(flag);
				throw new CoreException(new Status(IStatus.INFO, PLUGIN_ID, ENDER_error_msg_action_fix_problems, null));
			} // end if
			process();
			((ICompilationUnit) operation.getUnit().getJavaElement()).applyTextEdit(operation.createChange().getEdit(),
					null);
		} catch (CoreException e) {
			IStatus status = e.getStatus();
			if (null == status) {
				status = new Status(IStatus.ERROR, PLUGIN_ID, ENDER_error_title, e);
			} // end if
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
			notifyResult(false);
		} finally {
			notifyResult(result == 0);
		} // end try/catch/finally
	}// end run

	/**
	 * executes the {@link com.all9ssolutions.ender.business.Operation}
	 */
	private void process() {
		IWorkspaceRunnable operation = this.operation;
		try {
			IRunnableContext context = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (null == context) {
				context = new BusyIndicatorRunnableContext();
			} // end if
			ISchedulingRule schedulingRule = ResourcesPlugin.getWorkspace().getRoot();
			PlatformUI.getWorkbench().getProgressService().runInUI(context,
					new WorkbenchRunnableAdapter(operation, schedulingRule), schedulingRule);
		} catch (InvocationTargetException e) {
			IStatus status;
			if (null != e.getMessage() && e.getMessage().length() > 0) {
				status = new Status(IStatus.ERROR, PLUGIN_ID, ENDER_error_title, e);
			} else {
				status = new Status(IStatus.ERROR, PLUGIN_ID, ENDER_error_title, e.getTargetException());
			} // end if/else
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		} catch (InterruptedException e) {
			// eat it...
		} // end try/catch
	}// end process

	/**
	 * returns the primary instance of type for the Java class in the current editor
	 * 
	 * @return the primary instance of {@link org.eclipse.jdt.core.IType} for the
	 *         Java class in the current editor
	 * @throws JavaModelException exception if the primary type is unable to be
	 *                            reconciled
	 */
	private IType getTypeAtOffset() throws JavaModelException {
		IType type = null;
		if (null != editor.getEditorInput()) {
			ICompilationUnit unit = JavaUI.getWorkingCopyManager().getWorkingCopy(editor.getEditorInput());
			if (null != unit) {
				unit.reconcile(0, false, null, null);
				type = (IType) unit.getAncestor(IJavaElement.TYPE);
				if (null == type) {
					type = getPrimaryType(unit);
				} // end if
			} // end if
		} // end if
		return type;
	}// end getTypeAtOffset

	/**
	 * returns the primary top-level type for the compilation unit without relying on
	 * deprecated working-copy APIs.
	 *
	 * @param unit the compilation unit to inspect
	 * @return the primary type, or the first top-level type when no matching type is
	 *         present
	 * @throws JavaModelException exception if the unit types cannot be read
	 */
	private IType getPrimaryType(ICompilationUnit unit) throws JavaModelException {
		String elementName = unit.getElementName();
		int extensionIndex = elementName.lastIndexOf('.');
		String typeName = extensionIndex > 0 ? elementName.substring(0, extensionIndex) : elementName;
		IType type = unit.getType(typeName);
		if (type.exists()) {
			return type;
		} // end if
		IType[] types = unit.getTypes();
		return types.length > 0 ? types[0] : null;
	}// end getPrimaryType

	/**
	 * checks if the Java class in the current editor may be altered
	 * 
	 * @param element instance of {@link org.eclipse.jdt.core.IJavaElement} in the
	 *                current editor
	 * @return {@code true} if the class may be altered
	 * @throws CoreException exception if the Java class may not be altered
	 */
	private boolean isAlterable(IJavaElement element) throws CoreException {
		IJavaElement jelement = element;
		ICompilationUnit unit = (ICompilationUnit) jelement.getAncestor(IJavaElement.COMPILATION_UNIT);
		if (null != unit) {
			jelement = unit.getPrimary();
		} // end if
		IResource resource = jelement.getResource();
		if (!(resource.isSynchronized(IResource.FOLDER))) {
			String error = MessageFormat.format(ENDER_error_msg_action_out_of_sync,
					getPathLabel(resource.getFullPath()));
			throw new CoreException(
					new Status(IStatus.ERROR, PLUGIN_ID, ENDER_error_msg_action_cannot_perform, new Exception(error)));
		} // end if
		IResource readOnly = null;
		if ((resource.getType() == 1) && (null != resource.getResourceAttributes())
				&& (!resource.getResourceAttributes().isReadOnly())) {
			readOnly = resource;
		} // end if
		if (null == readOnly) {
			return true;
		} // end if
		Long oldTimeStamp = ((IFile) readOnly).getModificationStamp();
		IStatus status = ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { (IFile) readOnly }, shell);
		if (!(status.isOK())) {
			throw new CoreException(status);
		} // end if
		Long newTimeStamp = ((IFile) readOnly).getModificationStamp();
		if (!oldTimeStamp.equals(newTimeStamp)) {
			String error = MessageFormat.format(ENDER_error_msg_file_modified,
					getPathLabel(((IFile) readOnly).getFullPath()));
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, error, null));
		} // end if
		return true;
	}// end isAlterable

	/**
	 * gets the parameter path as a {@code String} value
	 * 
	 * @param path the instance of {@link org.eclipse.core.runtime.IPath} to get the
	 *             {@code String} value of
	 * @return {@code String} value of the parameter path
	 */
	private String getPathLabel(IPath path) {
		String testString = "args : String[]";
		boolean useTextProcessor = testString != TextProcessor.process(testString);
		String label = path.makeRelative().toString();
		return (!(useTextProcessor)) ? label : TextProcessor.process(label);
	}// end getPathLabel

	/**
	 * checks if the Java class in the current editor is editable
	 * 
	 * @param element instance of {@link org.eclipse.jdt.core.IJavaElement} in the
	 *                current editor
	 * @return {@code true} if the class may be altered
	 * @throws CoreException exception if the Java class may not be edited
	 */
	private boolean isEditable(IJavaElement element) throws CoreException {
		if (null == element) {
			throw new CoreException(
					new Status(Status.ERROR, PLUGIN_ID, ENDER_error_msg_action_not_on_build_path, null));
		} // end if
		if (element.getElementType() == 2) {
			return true;
		} // end if
		IJavaProject project = element.getJavaProject();
		try {
			if (!(project.isOnClasspath(element))) {
				throw new CoreException(
						new Status(Status.ERROR, PLUGIN_ID, ENDER_error_msg_action_not_on_class_path, null));
			} // end if
			IProject resourceProject = project.getProject();
			if (null == resourceProject) {
				throw new CoreException(
						new Status(Status.ERROR, PLUGIN_ID, ENDER_error_msg_action_not_java_project, null));
			} // end if
			IProjectNature nature = resourceProject.getNature("org.eclipse.jdt.core.javanature");
			if (null != nature) {
				return true;
			} // end if
		} catch (CoreException e) {
			throw e;
		} // end try/catch
		return true;
	}// end isEditable

	/**
	 * This nested class is used to execute the task.
	 *
	 */
	private class WorkbenchRunnableAdapter implements IRunnableWithProgress, IThreadListener {
		private IWorkspaceRunnable runnable;
		private ISchedulingRule rule;

		/**
		 * overloaded constructor instantiates this class
		 *
		 * @param runnable the instance of
		 *                 {@link org.eclipse.core.resources.IWorkspaceRunnable} to
		 *                 execute
		 * @param rule     the associated instance of
		 *                 {@link org.eclipse.core.runtime.jobs.ISchedulingRule}
		 */
		public WorkbenchRunnableAdapter(IWorkspaceRunnable runnable, ISchedulingRule rule) {
			super();
			this.runnable = runnable;
			this.rule = rule;
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.operation.IThreadListener#threadChange(java.lang.Thread)
		 */
		@Override
		public void threadChange(Thread thread) {
			Job.getJobManager().transferRule(rule, thread);
		}// end threadChange

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.
		 * runtime.IProgressMonitor)
		 */
		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				JavaCore.run(runnable, rule, monitor);
			} catch (OperationCanceledException e) {
				throw new InterruptedException(e.getMessage());
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} // end try/catch
		}// end run
	}// end nested class
}// end class

/**
 * This class is used to set a temporary context for executing the task.
 *
 */
class BusyIndicatorRunnableContext implements IRunnableContext {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.operation.IRunnableContext#run(boolean, boolean,
	 * org.eclipse.jface.operation.IRunnableWithProgress)
	 */
	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		BusyRunnable busyRunnable = new BusyRunnable(fork, runnable);
		BusyIndicator.showWhile(null, busyRunnable);
		Throwable throwable = busyRunnable.throwable;
		if (throwable instanceof InvocationTargetException) {
			throw ((InvocationTargetException) throwable);
		} // end if
		if (throwable instanceof InterruptedException) {
			throw ((InterruptedException) throwable);
		} // end if
	}// end run

	/**
	 * This nested class is used to customize an instance of
	 * {@link java.lang.Runnable} for the task.
	 *
	 */
	private class BusyRunnable implements Runnable {
		public Throwable throwable;
		private boolean fork;
		private IRunnableWithProgress runnable;

		/**
		 * overloaded constructor instantiates this class
		 *
		 * @param fork     {@code boolean} flag to indicate whether this task is forked
		 * @param runnable an instance of
		 *                 {@link org.eclipse.jface.operation.IRunnableWithProgress}
		 */
		public BusyRunnable(boolean fork, IRunnableWithProgress runnable) {
			super();
			this.fork = fork;
			this.runnable = runnable;
		}// end constructor

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				Thread thread = Thread.currentThread();
				if ((thread instanceof ThreadContext) || (ModalContext.isModalContextThread(thread))) {
					fork = false;
				} // end if
				if (fork) {
					ThreadContext t = new ThreadContext(runnable);
					t.start();
					t.sync();
					Throwable throwable = t.throwable;
					if (throwable != null) {
						if (throwable instanceof InvocationTargetException) {
							throw ((InvocationTargetException) throwable);
						} // end if
						if (throwable instanceof InterruptedException) {
							throw ((InterruptedException) throwable);
						} // end if
						if (throwable instanceof OperationCanceledException) {
							throw new InterruptedException();
						} // end if
						throw new InvocationTargetException(throwable);
					} // end if
				} else {
					try {
						runnable.run(new NullProgressMonitor());
					} catch (OperationCanceledException e) {
						throw new InterruptedException();
					} // end try/catch
				} // end if/else
			} catch (InvocationTargetException e) {
				throwable = e;
			} catch (InterruptedException e) {
				throwable = e;
			} // end try/catch
		}// end run

		/**
		 * This nested class is used to provide a context for the parent thread.
		 *
		 */
		private class ThreadContext extends Thread {
			IRunnableWithProgress runnable;
			Throwable throwable;

			/**
			 * overloaded constructor instantiates this class
			 *
			 * @param runnable an instance of
			 *                 {@link org.eclipse.jface.operation.IRunnableWithProgress}
			 */
			public ThreadContext(IRunnableWithProgress runnable) {
				super("BusyCursorRunnableContext-Thread");
				this.runnable = runnable;
			}// end constructor

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				try {
					runnable.run(new NullProgressMonitor());
				} catch (InvocationTargetException e) {
					throwable = e;
				} catch (InterruptedException e) {
					throwable = e;
					Thread.currentThread().interrupt();
				} catch (RuntimeException e) {
					throwable = e;
				} catch (Error e) {
					throwable = e;
				} // end try/catch
			}// end run

			/**
			 * joins this thread with the parent
			 */
			void sync() {
				try {
					join();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} // end try/catch
			}// end sync
		}// end nested class
	}// end nested class
}// end class
