
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

public class TrimDragPreferenceDialog extends Dialog {

	private Text   thresholdValue;
	private Button raggedTrimButton;
	
	public TrimDragPreferenceDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Label disclaimer = new Label(composite, SWT.BORDER | SWT.WRAP);
		disclaimer.setText("NOTE: This dialog is for testing purposes -only- and "+ //$NON-NLS-1$
				" will be removed from the code before release."); //$NON-NLS-1$
		disclaimer.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_RED));

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		GridData dgd = new GridData();
		dgd.grabExcessHorizontalSpace = true;
		dgd.horizontalSpan = 2;
		dgd.minimumWidth = 50;
		disclaimer.setLayoutData(dgd);
		
		Label tLabel = new Label(composite, SWT.NONE);
		tLabel.setText("Drag Threshold"); //$NON-NLS-1$
		
		thresholdValue = new Text(composite, SWT.SINGLE | SWT.BORDER);		
		thresholdValue.setText(Integer.toString(TrimDragPreferences.getThreshold()));
		thresholdValue.setToolTipText("The minimum distance to snap to"); //$NON-NLS-1$
		
		GridData tgd = new GridData();
		tgd.grabExcessHorizontalSpace = true;
		tgd.minimumWidth = 50;
		thresholdValue.setLayoutData(tgd);
		
		raggedTrimButton = new Button(composite, SWT.CHECK);
		raggedTrimButton.setText("Ragged Trim"); //$NON-NLS-1$
		raggedTrimButton.setSelection(TrimDragPreferences.showRaggedTrim());
		raggedTrimButton.setToolTipText("Allows trim in the same area to have different heights if checked"); //$NON-NLS-1$
		
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
