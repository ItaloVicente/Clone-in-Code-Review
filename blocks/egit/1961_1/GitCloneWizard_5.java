package org.eclipse.egit.ui.credentials;

import org.eclipse.egit.core.securestorage.EGitCredentials;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginDialog extends Dialog {

	private Text user;

	private Text password;

	private EGitCredentials credentials;

	private final URIish uri;

	private boolean isUserSet;

	private LoginDialog(Shell shell, URIish uri) {
		super(shell);
		this.uri = uri;
		isUserSet = uri.getUser() != null && uri.getUser().length() > 0;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));
		getShell().setText(UIText.LoginDialog_login);
		Label uriLabel = new Label(composite, SWT.NONE);
		uriLabel.setText(UIText.LoginDialog_repository);
		Text uriText = new Text(composite, SWT.READ_ONLY);
		uriText.setText(uri.toString());
		Label userLabel = new Label(composite, SWT.NONE);
		userLabel.setText(UIText.LoginDialog_user);
		if (isUserSet) {
			user = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			user.setText(uri.getUser());
		} else {
			user = new Text(composite, SWT.BORDER);
		}
		GridDataFactory.fillDefaults().grab(true, false).applyTo(user);
		Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setText(UIText.LoginDialog_password);
		password = new Text(composite, SWT.PASSWORD | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(password);
		if (isUserSet)
			password.setFocus();
		else
			user.setFocus();
		return composite;
	}

	public static EGitCredentials login(Shell parent, URIish uri) {
		LoginDialog dialog = new LoginDialog(parent, uri);
		if (dialog.open() == OK) {
			return dialog.credentials;
		}
		return null;
	}

	@Override
	protected void okPressed() {
		if (user.getText().length() > 0)
			credentials = new EGitCredentials(user.getText(),
					password.getText());
		super.okPressed();
	}

}
