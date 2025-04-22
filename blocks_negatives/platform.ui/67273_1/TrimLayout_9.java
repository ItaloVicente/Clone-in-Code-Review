/*******************************************************************************
 * Copyright (c) 2006, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.internal.layout;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.TrimDragPreferences;

/**
 * This dialog allows the User to modify the <code>TrimDragPreferences</code>.
 *
 * <p><b>
 * NOTE: this is a test harness at this time. This class may be removed
 * before the release of 3.2.
 * </b></p>
 *
 * @since 3.2
 *
 */
public class TrimDragPreferenceDialog extends Dialog {

	private Text   thresholdValue;
	private Button raggedTrimButton;

	/**
	 * @param parentShell
	 */
	public TrimDragPreferenceDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Label disclaimer = new Label(composite, SWT.BORDER | SWT.WRAP);
		disclaimer.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_RED));

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		GridData dgd = new GridData();
		dgd.grabExcessHorizontalSpace = true;
		dgd.horizontalSpan = 2;
		dgd.minimumWidth = 50;
		disclaimer.setLayoutData(dgd);

		Label tLabel = new Label(composite, SWT.NONE);

		thresholdValue = new Text(composite, SWT.SINGLE | SWT.BORDER);
		thresholdValue.setText(Integer.toString(TrimDragPreferences.getThreshold()));

		GridData tgd = new GridData();
		tgd.grabExcessHorizontalSpace = true;
		tgd.minimumWidth = 50;
		thresholdValue.setLayoutData(tgd);

		raggedTrimButton = new Button(composite, SWT.CHECK);
		raggedTrimButton.setSelection(TrimDragPreferences.showRaggedTrim());

		GridData rgd = new GridData();
		rgd.horizontalSpan = 2;
		raggedTrimButton.setLayoutData(rgd);

		return composite;
	}

	@Override
	protected void okPressed() {
		try {
			TrimDragPreferences.setThreshold(Integer.parseInt(thresholdValue.getText()));
		} catch (NumberFormatException e) {
		}

		boolean val = raggedTrimButton.getSelection();
		TrimDragPreferences.setRaggedTrim(val);

		super.okPressed();
	}

}
