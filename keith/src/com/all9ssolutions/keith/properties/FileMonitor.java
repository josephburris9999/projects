/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.properties;

import java.io.File;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is attached to specific files, allowing administrators to alter values at runtime.
 * <p>
 * <b>Concept contributed to and inspired by Dwayne T. Walker.</b>
 * </p>
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
class FileMonitor implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final FileMonitor INSTANCE = new FileMonitor();
	private Timer timer;
	private Hashtable<String, FileMonitorTask> entries;

	/**
	 * constructor is {@code private} to block instantiation
	 */
	private FileMonitor() {
		super();
		timer = new Timer(true);
		entries = new Hashtable<String, FileMonitorTask>();
	}

	/**
	 * @return static instance of this class
	 */
	static FileMonitor getInstance() {
		return INSTANCE;
	}

	/**
	 * add a {@link IFileMonitor} instance to the monitored file map
	 * 
	 * @param monitor {@code IFileMonitor} to add
	 * @param path    absolute path of the monitored file
	 * @param millis  time in milliseconds between file checks
	 */
	void addFileMonitor(IFileMonitor monitor, String path, long millis) {
		removeFileMonitor(monitor, path);
		FileMonitorTask task = new FileMonitorTask(monitor, path);
		entries.put(path + monitor.hashCode(), task);
		timer.schedule(task, millis, millis);
	}

	/**
	 * remove a {@link IFileMonitor} instance from the monitored file map
	 * 
	 * @param monitor {@code IFileMonitor} to add
	 * @param path    absolute path of the monitored file
	 */
	void removeFileMonitor(IFileMonitor monitor, String path) {
		FileMonitorTask task = (FileMonitorTask) entries.remove(path + monitor.hashCode());
		if (null != task) {
			task.cancel();
		}
	}

	/**
	 * fires a file change event, triggering a reload of the monitored file
	 * 
	 * @param monitor {@code IFileMonitor} to add
	 * @param path    absolute path of the monitored file
	 */
	protected void fireFileChangeEvent(IFileMonitor monitor, String path) {
		monitor.fileChanged(path);
	}

	/**
	 * This nested class models a {@code Runnable} task for monitoring an external file.
	 * <p>
	 * <b>Concept contributed to and inspired by Dwayne T. Walker.</b>
	 * </p>
	 * 
	 * @author Joseph Burris, all9s Solutions LLC
	 */
	private class FileMonitorTask extends TimerTask {
		private IFileMonitor monitor;
		private String path;
		private long lastModified;

		/**
		 * overloaded constructor instantiates this class
		 * 
		 * @param monitor {@code IFileMonitor} to add
		 * @param path    absolute path of the monitored file
		 */
		private FileMonitorTask(IFileMonitor monitor, String path) {
			super();
			this.monitor = monitor;
			this.path = path;
			this.lastModified = new File(path).lastModified();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			long modified = new File(path).lastModified();
			if (modified != this.lastModified) {
				this.lastModified = modified;
				fireFileChangeEvent(this.monitor, this.path);
			}
		}
	}

	/**
	 * replaces the use of a compiler-generated {@code readResolve()} method which is called from the {@code readObject()} method within the serialization machinery
	 * <p>
	 * the {@code readObject()} method will create a new instance of the serialized class and call the {@code readResolve()} thereafter
	 * </p>
	 * <p>
	 * providing this method, the singleton pattern will not be violated with an additional instance of the class since this method returns the singleton instance.
	 * </p>
	 * 
	 * @return singleton instance of this class
	 * @throws ObjectStreamException exception if the serialization machinery fails on this method
	 */
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
}
