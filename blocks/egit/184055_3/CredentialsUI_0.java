package org.eclipse.egit.core.credentials;

import java.io.IOException;

import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.transport.URIish;

public interface CredentialsStore {

	void putCredentials(@NonNull URIish uri,
			@NonNull UserPasswordCredentials credentials)
			throws StorageException, IOException;

	@Nullable
	UserPasswordCredentials getCredentials(@NonNull URIish uri)
			throws StorageException;

	void clearCredentials(@NonNull URIish uri) throws IOException;
}
