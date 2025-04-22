package org.eclipse.egit.ui.credentials;

import java.io.IOException;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.core.securestorage.UserPasswordCredentials;
import org.eclipse.egit.ui.UIText;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.widgets.Shell;

public class LoginService {

	public static UserPasswordCredentials login(Shell parent, URIish uri) {
		LoginDialog dialog = new LoginDialog(parent, uri);
		if (dialog.open() == Window.OK) {
			UserPasswordCredentials credentials = dialog.getCredentials();
			if (credentials != null && dialog.getStoreInSecureStore())
				storeCredentials(uri, credentials);
			return credentials;
		}
		return null;
	}

	public static UserPasswordCredentials changeCredentials(Shell parent,
			URIish uri) {
		LoginDialog dialog = new LoginDialog(parent, uri);
		dialog.setChangeCredentials(true);
		if (dialog.open() == Window.OK) {
			UserPasswordCredentials credentials = dialog.getCredentials();
			if (credentials != null)
				storeCredentials(uri, credentials);
			return credentials;
		}
		return null;
	}


	private static void storeCredentials(URIish uri,
			UserPasswordCredentials credentials) {
		try {
			org.eclipse.egit.core.Activator.getDefault().getSecureStore()
					.putCredentials(uri, credentials);
		} catch (StorageException e) {
			Activator.handleError(UIText.LoginService_storingCredentialsFailed, e, true);
		} catch (IOException e) {
			Activator.handleError(UIText.LoginService_storingCredentialsFailed, e, true);
		}
	}

}
