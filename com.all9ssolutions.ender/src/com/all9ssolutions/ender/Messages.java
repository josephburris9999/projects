package com.all9ssolutions.ender;

import org.eclipse.osgi.util.NLS;

/**
 * This class initializes messages for use in the application.
 * 
 * @see org.eclipse.osgi.util.NLS
 */
public class Messages {

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

	/**
	 * error message label
	 */
	public static String ENDER_error_title;

	/**
	 * message format to use for the file modified error message
	 */
	public static String ENDER_error_msg_file_modified;

	/**
	 * message format to use for the unsaved editor error message
	 */
	public static String ENDER_error_msg_action_editor;

	/**
	 * message format to use for the not applicable error message
	 */
	public static String ENDER_error_msg_action_not_applicable;

	/**
	 * message format to use for the problems exist error message
	 */
	public static String ENDER_error_msg_action_fix_problems;

	/**
	 * message format to use for the out of sync error message
	 */
	public static String ENDER_error_msg_action_out_of_sync;

	/**
	 * message format to use for the performance error message
	 */
	public static String ENDER_error_msg_action_cannot_perform;

	/**
	 * message format to use for the build path error message
	 */
	public static String ENDER_error_msg_action_not_on_build_path;

	/**
	 * message format to use for the class path error message
	 */
	public static String ENDER_error_msg_action_not_on_class_path;

	/**
	 * message format to use for the java project error message
	 */
	public static String ENDER_error_msg_action_not_java_project;

	static {
		NLS.initializeMessages("com.all9ssolutions.ender.messages", Messages.class);
	}// end static

	/**
	 * default constructor made {@code private} to block instantiation
	 * <p>
	 * all fields in this class should be {@code static}
	 * </p>
	 */
	private Messages() {
		super();
	}// end constructor
}// end class