/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.dynamic;

import org.eclipse.help.IContext;
import org.eclipse.ui.help.AbstractHelpUI;

/**
 * @since 3.1
 */
public class DynamicHelpSupport extends AbstractHelpUI {

	@Override
	public void displayHelp() {

	}

	@Override
	public void displayContext(IContext context, int x, int y) {

	}

	@Override
	public void displayHelpResource(String href) {

	}

	@Override
	public boolean isContextHelpDisplayed() {
		return false;
	}

}
