/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     René Brandstetter - Bug 431707 - [QuickAccess] Quick Access should open a dialog if hidden
 *******************************************************************************/
package org.eclipse.ui.internal.quickaccess;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchWindow;

/**
 * Handler for quick access pop-up dialog, showing UI elements such as editors,
 * views, commands.
 *
 */
public class QuickAccessHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent executionEvent) {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(executionEvent);
		if (window == null) {
			return null;
		}

		MWindow mWindow = ((WorkbenchWindow) window).getModel();
		EModelService modelService = mWindow.getContext().get(EModelService.class);
		MToolControl searchField = (MToolControl) modelService.find("SearchField", mWindow); //$NON-NLS-1$
		if (searchField != null && searchField.isVisible()) {
			Control control = (Control) searchField.getWidget();
			if (control != null && control.isVisible()) {
				Control previousFocusControl = control.getDisplay().getFocusControl();
				control.setFocus();
				SearchField field = (SearchField) searchField.getObject();
				field.activate(previousFocusControl);
				return null;
			}
		}

		displayQuickAccessDialog(window, executionEvent.getCommand());
		return null;
	}

	/**
	 * Utility method to displays the original/legacy QuickAccess dialog.
	 *
	 * @param window
	 *            the active workbench window
	 * @param command
	 *            the command which invokes the open of the dialog
	 */
	private static void displayQuickAccessDialog(IWorkbenchWindow window, Command command) {
		PopupDialog popupDialog = new QuickAccessDialog(window, command);
		popupDialog.open();
	}

}
