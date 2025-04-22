package org.eclipse.egit.ui.internal;

import java.io.IOException;

import org.eclipse.egit.core.securestorage.UserPasswordCredentials;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jgit.transport.URIish;

public class SecureStoreUtils {
	public static boolean storeCredentials(UserPasswordCredentials credentials,
			URIish uri) {
		if (credentials != null) {
			try {
				org.eclipse.egit.core.Activator.getDefault().getSecureStore()
						.putCredentials(uri, credentials);
			} catch (StorageException e) {
				Activator.handleError(
						UIText.SecureStoreUtils_writingCredentialsFailed, e,
						true);
				return false;
			} catch (IOException e) {
				Activator.handleError(
						UIText.SecureStoreUtils_writingCredentialsFailed, e,
						true);
				return false;
			}
		}
		return true;
	}
}
