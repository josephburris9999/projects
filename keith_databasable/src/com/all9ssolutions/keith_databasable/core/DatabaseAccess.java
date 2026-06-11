/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_databasable.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * This class is a wrapper for the basic operations in accessing a database, providing methods to {@link #getConnection()}, {@link #destroy(Connection, PreparedStatement, ResultSet, Logger)}, and {@link #logWarnings(SQLWarning, Logger)}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class DatabaseAccess {
	private static final Logger logger = Logger.getLogger("com.all9ssolutions.keith_databasable.core.DatabaseAccess");
	/**
	 * class variable contains the database access properties for a current instance
	 */
	private DatabaseProperties properties;

	/**
	 * overloaded constructor instantiates this class
	 * 
	 * @param id unique database ID
	 * @throws RuntimeException exception if the parameter is {@code null} or blank
	 * @throws SQLException     exception if the external database properties are invalid
	 */
	public DatabaseAccess(String id) throws SQLException {
		super();
		if (null == id || "".equals(id.trim())) {
			throw new RuntimeException("The database ID is null or empty. No database connection model exists with that ID.");
		}
		properties = new DatabaseProperties(id);
		if (!properties.isValid()) {
			throw new SQLException("The database properties are invalid for database ID: " + String.valueOf(id));
		}
	}

	/**
	 * get a database connection using the configured database properties
	 * 
	 * @return a database {@link Connection}
	 * @throws SQLException exception if the logic fails for any reason
	 */
	public Connection getConnection() throws SQLException {
		return getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
	}

	/**
	 * convenience method for getting a database connection using values not configured in an external properties file
	 * <p>
	 * WARNING: the database driver class for the connection must be initialized at some point before this method is called
	 * </p>
	 * 
	 * @param url      the database URL
	 * @param username the database user name
	 * @param password the database password
	 * @return a database {@link Connection}
	 * @throws SQLException exception if the logic fails for any reason
	 */
	public static Connection getConnection(String url, String username, String password) throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			AbstractApplication.getLoggingProperties().setLoggingLevel(Level.CONFIG);
			throw new SQLException(e.getMessage(), e);
		}
		if (null != conn) {
			logWarningsFromConnection(conn, logger);
		}
		return conn;
	}

	/**
	 * convenience method to destroy a database {@link Connection}, {@link PreparedStatement}, and {@link ResultSet}
	 * <p>
	 * for queries without a {@code ResultSet}, {@code null} should be substituted
	 * </p>
	 * 
	 * @param conn   the database {@code Connection}
	 * @param pstmt  the {@code PreparedStatement}
	 * @param rs     the {@code ResultSet}
	 * @param logger the logger to use for resulting messages
	 */
	public static void destroy(Connection conn, PreparedStatement pstmt, ResultSet rs, Logger logger) {
		logger = getLogger(logger);
		try {
			if (null != rs) {
				logWarningsFromResultSet(rs, logger);
				rs.close();
			}
		} catch (SQLException e) {
			logException(e, logger);
		}
		try {
			if (null != pstmt) {
				logWarningsFromStatement(pstmt, logger);
				pstmt.close();
			}
		} catch (SQLException e) {
			logException(e, logger);
		}
		try {
			if (null != conn) {
				logWarningsFromConnection(conn, logger);
				if (!conn.getAutoCommit()) {
					conn.setAutoCommit(true);
				}
				conn.close();
			}
		} catch (SQLException e) {
			logException(e, logger);
		}
	}

	/**
	 * convenience method for logging warnings from a {@link PreparedStatement}
	 * 
	 * @param pstmt  the {@code PreparedStatement}
	 * @param logger the logger to use for resulting messages
	 * @throws SQLException exception if the {@code PreparedStatement} is {@code null} or the warnings are inaccessible
	 */
	public static void logWarningsFromStatement(PreparedStatement pstmt, Logger logger) throws SQLException {
		if (null == pstmt) {
			throw new SQLException("Unable to log warnings from a null PreparedStatement.");
		}
		logger = getLogger(logger);
		logWarnings(pstmt.getWarnings(), logger);
	}

	/**
	 * convenience method for logging warnings from a database {@link Connection}
	 * 
	 * @param conn   the database {@code Connection}
	 * @param logger the logger to use for resulting messages
	 * @throws SQLException exception if the database {@code Connection} is {@code null} or the warnings are inaccessible
	 */
	public static void logWarningsFromConnection(Connection conn, Logger logger) throws SQLException {
		if (null == conn) {
			throw new SQLException("Unable to log warnings from a null Connection.");
		}
		logger = getLogger(logger);
		logWarnings(conn.getWarnings(), logger);
	}

	/**
	 * convenience method for logging warnings from a {@link ResultSet}
	 * 
	 * @param rs     the {@code ResultSet}
	 * @param logger the logger to use for resulting messages
	 * @throws SQLException exception if the {@code ResultSet} is {@code null} or the warnings are inaccessible
	 */
	public static void logWarningsFromResultSet(ResultSet rs, Logger logger) throws SQLException {
		if (null == rs) {
			throw new SQLException("Unable to log warnings from a null ResultSet.");
		}
		logger = getLogger(logger);
		logWarnings(rs.getWarnings(), logger);
	}

	/**
	 * convenience method with recursive logic for logging {@link SQLWarning}s
	 * 
	 * @param warning the {@code SQLWarning}
	 * @param logger  the logger to use for resulting messages
	 */
	public static void logWarnings(SQLWarning warning, Logger logger) {
		logger = getLogger(logger);
		if (null != warning) {
			logger.warning("---Warning---");
			while (null != warning) {
				logger.warning("Message: " + warning.getMessage());
				logger.warning("SQLState: " + warning.getSQLState());
				logger.warning("Vendor error code: " + warning.getErrorCode());
				warning = warning.getNextWarning();
			}
		}
	}

	/**
	 * convenience method with recursive logic for logging {@link SQLException}s
	 * 
	 * @param exception the {@code SQLException}
	 * @param logger    the logger to use for resulting messages
	 */
	public static void logException(SQLException exception, Logger logger) {
		logger = getLogger(logger);
		while (null != exception) {
			logger.log(Level.SEVERE, "Stack Trace: " + exception.getMessage(), exception);
			logger.severe("SQLState: " + exception.getSQLState());
			logger.severe("Error Code: " + exception.getErrorCode());
			logger.severe("Message: " + exception.getMessage());
			Throwable throwable = exception.getCause();
			while (null != throwable) {
				logger.log(Level.SEVERE, "Cause: " + throwable, throwable);
				throwable = throwable.getCause();
			}
			exception = exception.getNextException();
		}
	}

	/**
	 * checks if the parameter is {@code null} and sets the {@link Logger} to this class' logger if so
	 * 
	 * @param logger the logger to use for resulting messages
	 */
	private static Logger getLogger(Logger logger) {
		if (null == logger) {
			logger = DatabaseAccess.logger;
			logger.warning("Logger parameter is null. Using DatabaseAccess logger instead.");
		}
		return logger;
	}
}
