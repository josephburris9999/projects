/**
 * @(#)Handler.java 1.0.0 Copyright (c) 2024 all9s Solutions, All Rights Reserved.
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
package com.all9ssolutions.lumberjack.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.all9ssolutions.lumberjack.actions.Action;

/**
 * This class is used to handle requests from multiple access points to execute
 * the {@link com.all9ssolutions.ender.actions.Action}.
 *
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Handler extends AbstractHandler {

	/**
	 * default constructor instantiates this class
	 */
	public Handler() {
        super();
    }// end constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        Action action = new Action(window);
        action.run();
        return null;
    }// end execute
}// end class
