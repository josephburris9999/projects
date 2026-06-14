package com.all9ssolutions.lumberjack;

import org.eclipse.osgi.util.NLS;

/**
 * This class initializes messages for use in the application.
 * 
 * @see org.eclipse.osgi.util.NLS
 */
public class Messages {

	/**
	 * wizard selections page title
	 */
	public static String LUMBERJACK_selections_title;

	/**
	 * wizard selections page description
	 */
	public static String LUMBERJACK_selections_description;

	/**
	 * wizard selections page class icon path
	 */
	public static String LUMBERJACK_selections_class_obj;

	/**
	 * wizard selections page private method icon path
	 */
	public static String LUMBERJACK_selections_private_method_obj;

	/**
	 * wizard selections page protected method icon path
	 */
	public static String LUMBERJACK_selections_protected_method_obj;

	/**
	 * wizard selections page public method icon path
	 */
	public static String LUMBERJACK_selections_public_method_obj;

	/**
	 * wizard selections page selected methods message
	 */
	public static String LUMBERJACK_selections_methods;

	/**
	 * wizard selections page no selected methods message
	 */
	public static String LUMBERJACK_selections_no_methods;

	/**
	 * wizard preview page title
	 */
	public static String LUMBERJACK_preview_title;

	/**
	 * wizard preview page description
	 */
	public static String LUMBERJACK_preview_description;

	/**
	 * wizard preview page compilation unit label
	 */
	public static String LUMBERJACK_preview_compilation_unit;

	/**
	 * wizard selections page java compilation unit icon path
	 */
	public static String LUMBERJACK_preview_icon;

	/**
	 * message format to use for the comment message
	 */
	public static String LUMBERJACK_logging_msg_comment;

	/**
	 * message format to use for the no Javadoc message
	 */
	public static String LUMBERJACK_logging_msg_no_javadoc;

	/**
	 * message format to use for the {@code break} comment
	 */
	public static String LUMBERJACK_logging_msg_break;

	/**
	 * message format to use for the {@code continue} comment
	 */
	public static String LUMBERJACK_logging_msg_continue;

	/**
	 * message format to use for the {@code synchronized} comment
	 */
	public static String LUMBERJACK_logging_msg_synchronized;

	/**
	 * message format to use for the variable comment
	 */
	public static String LUMBERJACK_logging_msg_variable;

	/**
	 * message format to use for the {@code if} comment
	 */
	public static String LUMBERJACK_logging_msg_if;

	/**
	 * message format to use for the {@code else} comment
	 */
	public static String LUMBERJACK_logging_msg_else;

	/**
	 * message format to use for the {@code do} comment
	 */
	public static String LUMBERJACK_logging_msg_do;

	/**
	 * message format to use for the {@code while} comment
	 */
	public static String LUMBERJACK_logging_msg_while;

	/**
	 * message format to use for the enhanced {@code for} comment
	 */
	public static String LUMBERJACK_logging_msg_enhanced_for;

	/**
	 * message format to use for the {@code for} comment
	 */
	public static String LUMBERJACK_logging_msg_for;

	/**
	 * message format to use for the {@code switch} comment
	 */
	public static String LUMBERJACK_logging_msg_switch;

	/**
	 * message format to use for the method comment
	 */
	public static String LUMBERJACK_logging_msg_method;

	/**
	 * error message label
	 */
	public static String LUMBERJACK_error_title;

	/**
	 * message format to use for the file modified error message
	 */
	public static String LUMBERJACK_error_msg_file_modified;

	/**
	 * message format to use for the unsaved editor error message
	 */
	public static String LUMBERJACK_error_msg_action_editor;

	/**
	 * message format to use for the not applicable error message
	 */
	public static String LUMBERJACK_error_msg_action_not_applicable;

	/**
	 * message format to use for the problems exist error message
	 */
	public static String LUMBERJACK_error_msg_action_fix_problems;

	/**
	 * message format to use for the no methods error message
	 */
	public static String LUMBERJACK_error_msg_action_no_methods;

	/**
	 * message format to use for the out of sync error message
	 */
	public static String LUMBERJACK_error_msg_action_out_of_sync;

	/**
	 * message format to use for the performance error message
	 */
	public static String LUMBERJACK_error_msg_action_cannot_perform;

	/**
	 * message format to use for the build path error message
	 */
	public static String LUMBERJACK_error_msg_action_not_on_build_path;

	/**
	 * message format to use for the class path error message
	 */
	public static String LUMBERJACK_error_msg_action_not_on_class_path;

	/**
	 * message format to use for the java project error message
	 */
	public static String LUMBERJACK_error_msg_action_not_java_project;

	static {
		NLS.initializeMessages("com.all9ssolutions.lumberjack.messages", Messages.class);
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