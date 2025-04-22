/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.part;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * A message page display a message in a pagebook view.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @see PageBookView
 * @noextend This class is not intended to be subclassed by clients.
 */
public class MessagePage extends Page {
    private Composite pgComp;

    private Label msgLabel;


    /**
     * Creates a new page. The message is the empty string.
     */
    public MessagePage() {
    }

    @Override
	public void createControl(Composite parent) {
        pgComp = new Composite(parent, SWT.NULL);
        pgComp.setLayout(new FillLayout());

        msgLabel = new Label(pgComp, SWT.LEFT | SWT.TOP | SWT.WRAP);
        msgLabel.setText(message);
    }

    @Override
	public Control getControl() {
        return pgComp;
    }

    /**
     * Sets focus to a part in the page.
     */
    @Override
	public void setFocus() {
        pgComp.setFocus();
    }

    /**
     * Sets the message to the given string.
     *
     * @param message the message text
     */
    public void setMessage(String message) {
        this.message = message;
        if (msgLabel != null) {
			msgLabel.setText(message);
		}
    }
}
