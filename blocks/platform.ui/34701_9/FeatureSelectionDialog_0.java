package org.eclipse.jface.dialogs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public abstract class SelectionDialog<T> extends TrayDialog {
	private Collection<T> result;

	private Collection<T> initialSelection;

	private String title;

	private String message = ""; //$NON-NLS-1$

	private int dialogBoundsStrategy = Dialog.DIALOG_PERSISTLOCATION | Dialog.DIALOG_PERSISTSIZE;

	private IDialogSettings dialogBoundsSettings = null;

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

	protected Label createMessageArea(Composite composite) {
		Label label = new Label(composite, SWT.NONE);
		if (message != null) {
			label.setText(message);
		}
		label.setFont(composite.getFont());
		return label;
	}

	protected Collection<T> getInitialSelection() {
		if (null == initialSelection) {
			return Collections.emptyList();
		}
		return initialSelection;
	}

	protected String getMessage() {
		return message;
	}

	public Collection<T> getResult() {
		return result;
	}

	public void setInitialSelection(T... selectedElements) {
		initialSelection = Arrays.asList(selectedElements);
	}

	public void setInitialSelection(Collection<T> selectedElements) {
		initialSelection = selectedElements;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected void setResult(Collection<T> newUserSelection) {
		if (newUserSelection == null) {
			result = Collections.emptyList();
		} else {
			result = newUserSelection;
		}
	}

	protected void setResult(T... newUserSelection) {
		if (newUserSelection == null) {
			result = Collections.emptyList();
		} else {
			result = Arrays.asList(newUserSelection);
		}
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
