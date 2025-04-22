/*******************************************************************************
 * Copyright (C) 2010, Jens Baumgart <jens.baumgart@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.common;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public class PushResultDialogTester {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public void assertResultMessage(String expectedMessage) {
		bot.styledText(expectedMessage);
	}

	public void closeDialog() {
		bot.button(IDialogConstants.OK_LABEL).click();
	}

}
