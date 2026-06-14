// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.listeners;

import java.io.IOException;

import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class EverythingListener.
 *
 */
@WebListener
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class EverythingListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener,
		HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, HttpSessionIdListener,
		ServletRequestListener, ServletRequestAttributeListener, AsyncListener {

	/**
	 * default constructor instantiates this class
	 */
	public EverythingListener() {
		super();
	}// end constructor
	public void sessionCreated(HttpSessionEvent event) {
	}// end sessionCreated
	public void attributeRemoved(ServletContextAttributeEvent event) {
	}// end attributeRemoved
	public void onError(AsyncEvent event) throws IOException {
	}// end onError
	public void sessionIdChanged(HttpSessionEvent event, String s) {
	}// end sessionIdChanged
	public void attributeAdded(jakarta.servlet.ServletRequestAttributeEvent event) {
	}// end attributeAdded
	public void onTimeout(jakarta.servlet.AsyncEvent event) throws IOException {
	}// end onTimeout
	public void attributeReplaced(jakarta.servlet.http.HttpSessionBindingEvent event) {
	}// end attributeReplaced
	public void sessionWillPassivate(HttpSessionEvent event) {
	}// end sessionWillPassivate
	public void contextInitialized(ServletContextEvent event) {
	}// end contextInitialized
	public void attributeAdded(ServletContextAttributeEvent event) {
	}// end attributeAdded
	public void onComplete(AsyncEvent event) throws IOException {
	}// end onComplete
	public void requestDestroyed(ServletRequestEvent event) {
	}// end requestDestroyed
	public void attributeRemoved(ServletRequestAttributeEvent event) {
	}// end attributeRemoved
	public void onStartAsync(AsyncEvent event) throws IOException {
	}// end onStartAsync
	public void valueBound(HttpSessionBindingEvent event) {
	}// end valueBound
	public void requestInitialized(ServletRequestEvent event) {
	}// end requestInitialized
	public void sessionDestroyed(HttpSessionEvent event) {
	}// end sessionDestroyed
	public void sessionDidActivate(HttpSessionEvent event) {
	}// end sessionDidActivate
	public void contextDestroyed(ServletContextEvent event) {
	}// end contextDestroyed
	public void attributeReplaced(ServletRequestAttributeEvent event) {
	}// end attributeReplaced
	public void attributeAdded(HttpSessionBindingEvent event) {
	}// end attributeAdded
	public void attributeRemoved(HttpSessionBindingEvent event) {
	}// end attributeRemoved
	public void attributeReplaced(ServletContextAttributeEvent event) {
	}// end attributeReplaced
	public void valueUnbound(HttpSessionBindingEvent event) {
	}// end valueUnbound
}// end class
