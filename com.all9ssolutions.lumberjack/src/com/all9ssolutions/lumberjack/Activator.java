/**
 * @(#)Activator.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.lumberjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This activator class controls the Lumberjack plug-in life cycle.
 *
 * @see org.eclipse.ui.plugin.AbstractUIPlugin
 */
public class Activator extends AbstractUIPlugin {
	/**
	 * The plug-in unique identifier.
	 */
	public static final String PLUGIN_ID = "com.all9ssolutions.lumberjack";
	private static Activator plugin;
	private static ResourceBundle bundle;

	private String qualifiedClassPath;
	private MethodMap methodMap;

	/**
	 * default constructor instantiates this class
	 */
	public Activator() {
		super();
	}// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		methodMap = new MethodMap();
	}// end start

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		methodMap = null;
		super.stop(context);
	}// end stop

	/**
	 * returns the shared instance of this class
	 *
	 * @return the shared {@link com.all9ssolutions.lumberjack.Activator} instance
	 */
	public static Activator getDefault() {
		return plugin;
	}// end getDefault

	/**
	 * returns the shared instance of plug-in properties
	 * 
	 * @return the shared {@link ResourceBundle} instance
	 */
	public ResourceBundle getResourceBundle() {
		if (null == bundle) {
			try {
				bundle = ResourceBundle.getBundle("plugin", Locale.getDefault());
			} catch (MissingResourceException e) {
				getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,
						"The plugin.properties file could not be found.", e));
			} // end try/catch
		} // end if
		return bundle;
	}// end getResourceBundle

	/**
	 * returns the image descriptor for the image file at the given plug-in relative
	 * path
	 *
	 * @param path image path
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}// end getImageDescriptor

	/**
	 * returns the qualified class path for the Java class in the current editor
	 * 
	 * @return the qualified class path for the Java class in the current editor
	 */
	public String getQualifiedClassPath() {
		return null == qualifiedClassPath ? "" : qualifiedClassPath;
	}// end getQualifiedClassPath

	/**
	 * sets the qualified class path variable value
	 * 
	 * @param qualifiedClassPath the qualified class path to set
	 */
	public void setQualifiedClassPath(String qualifiedClassPath) {
		this.qualifiedClassPath = qualifiedClassPath;
	}// end setQualifiedClassPath

	/**
	 * returns the {@link MethodMap} for the Java class in the current editor
	 * 
	 * @return the map of methods to generate logging statements for
	 */
	public MethodMap getMethodMap() {
		return methodMap;
	}// end getMethodMap

	/**
	 * This nested class is used to model the
	 * {@link org.eclipse.jdt.core.dom.TypeDeclaration}s and
	 * {@link org.eclipse.jdt.core.dom.MethodDeclaration}s within a java class.
	 *
	 */
	public class MethodMap extends HashMap<TypeDeclaration, List<MethodDeclaration>> {
		private static final long serialVersionUID = 1L;
		private final String newLine = System.lineSeparator();

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder("MethodMap [");
			List<MethodDeclaration> value;
			for (TypeDeclaration key : keySet()) {
				result.append(key.getName()).append("=");
				value = get(key);
				for (int i = 0, j = value.size(), k = value.size() - 1; i < j; i++) {
					result.append(value.get(i));
					if (i != k) {
						result.append(",");
					} // end if
				} // end for
				result.append(newLine);
			} // end for
			result.append("]");
			return result.toString();
		}// end toString

		/**
		 * converts the parameter {@code Object[]} to a
		 * {@code org.eclipse.jdt.core.dom.MethodDeclaration[]} and adds the content as
		 * an element of this class {@code methods} variable.
		 *
		 * @param values the client selected values from the display tree
		 */
		public void set(Object[] values) {
			clear();
			if (null != values && values.length > 0) {
				TypeDeclaration type;
				MethodDeclaration method;
				List<MethodDeclaration> methods;
				for (int i = 0, j = values.length; i < j; i++) {
					if (values[i] instanceof MethodDeclaration) {
						method = (MethodDeclaration) values[i];
						if (method.getParent() instanceof TypeDeclaration) {
							type = (TypeDeclaration) method.getParent();
							methods = (null == get(type)) ? new ArrayList<MethodDeclaration>() : get(type);
							methods.add((MethodDeclaration) values[i]);
							put(type, methods);
						} // end if
					} // end if
				} // end for
			} // end if
		}// end set
	}// end nested class
	
	/**
	 * This class initializes Lumberjack messages for use in the application.
	 * 
	 */
	public static class Messages {

		/**
		 * label indicating the in-line comment style selection
		 */
		public static String ENDER_inline_label;

		/**
		 * label indicating the multi-line comment style selection
		 */
		public static String ENDER_multiline_label;

		/**
		 * message format to use for the in-line comment style
		 */
		public static String ENDER_actual_inline_comment;

		/**
		 * message format to use for the multi-line comment style
		 */
		public static String ENDER_actual_multiline_comment;

		static {
			NLS.initializeMessages("com.all9ssolutions.lumberjack.messages", Messages.class);
		}// end static
	}// end nested class
}// end class
