/*******************************************************************************
 * Copyright (c) 2007, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.examples.contributions.view;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.examples.contributions.ContributionMessages;
import org.eclipse.ui.examples.contributions.model.Person;
import org.eclipse.ui.examples.contributions.model.PersonInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * open an element for editing.
 * 
 * @since 3.3
 */
public class EditInfoHandler extends AbstractHandler {
	


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (window == null) {
			return null;
		}
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if (sel == null) {
			sel = HandlerUtil.getCurrentSelection(event);
		}
		if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Person person = (Person) selection.getFirstElement();
			PersonInput input = new PersonInput(person.getId());
			try {
				window.getActivePage().openEditor(input, EDITOR_ID);
			} catch (PartInitException e) {
				throw new ExecutionException(
						ContributionMessages.EditInfoHandler_failed_to_open, e);
			}
		}
		return null;
	}

}
