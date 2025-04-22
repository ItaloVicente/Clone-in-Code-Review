package org.eclipse.jface.dialogs;

public interface IDialogSettingsProvider {

	IDialogSettings loadDialogSettings();

	void saveDialogSettings();

	IDialogSettings getDialogSettings();

}
