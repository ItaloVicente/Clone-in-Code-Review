package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.WorkbenchMessages;

public abstract class SelectionDialog extends TrayDialog {
	private Object[] result;

	private List initialSelections = new ArrayList();

	private String title;

	private String message = ""; //$NON-NLS-1$

	private int dialogBoundsStrategy = Dialog.DIALOG_PERSISTLOCATION | Dialog.DIALOG_PERSISTSIZE;

	private IDialogSettings dialogBoundsSettings = null;

	static String SELECT_ALL_TITLE = WorkbenchMessages.SelectionDialog_selectLabel;

	static String DESELECT_ALL_TITLE = WorkbenchMessages.SelectionDialog_deselectLabel;

	protected SelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected Label createMessageArea(Composite composite) {
		Label label = new Label(composite, SWT.NONE);
		if (message != null) {
			label.setText(message);
		}
		label.setFont(composite.getFont());
		return label;
	}

	@Deprecated
	protected List getInitialSelections() {
		if (initialSelections.isEmpty()) {
			return null;
		}
		return getInitialElementSelections();
	}

	protected List getInitialElementSelections() {
		return initialSelections;
	}

	protected String getMessage() {
		return message;
	}

	public Button getOkButton() {
		return getButton(IDialogConstants.OK_ID);
	}

	public Object[] getResult() {
		return result;
	}

	public void setInitialSelections(Object[] selectedElements) {
		initialSelections = new ArrayList(selectedElements.length);
		for (int i = 0; i < selectedElements.length; i++) {
			initialSelections.add(selectedElements[i]);
		}
	}

	public void setInitialElementSelections(List selectedElements) {
		initialSelections = selectedElements;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected void setResult(List newResult) {
		if (newResult == null) {
			result = null;
		} else {
			result = new Object[newResult.size()];
			newResult.toArray(result);
		}
	}

	protected void setSelectionResult(Object[] newResult) {
		result = newResult;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDialogBoundsSettings(IDialogSettings settings, int strategy) {
		dialogBoundsStrategy = strategy;
		dialogBoundsSettings = settings;
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		return dialogBoundsSettings;
	}

	@Override
	protected int getDialogBoundsStrategy() {
		return dialogBoundsStrategy;
	}
	
    @Override
	protected boolean isResizable() {
    	return true;
    }
}
