/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

/**
 * @since 3.5
 * @author Prakash G.R.
 */
public class RadioStateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		if(HandlerUtil.matchesRadioState(event))
		 {
		}

		String currentState = event.getParameter(RadioState.PARAMETER_ID);
		HandlerUtil.updateRadioState(event.getCommand(), currentState);

		return null;
	}

}
