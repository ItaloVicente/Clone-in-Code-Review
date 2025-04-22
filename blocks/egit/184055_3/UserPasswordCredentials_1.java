package org.eclipse.egit.core.credentials;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.URIish;

public interface CredentialsUI {

	boolean fillCredentials(URIish uri, CredentialItem... items);

	@Nullable UserPasswordCredentials getCredentials(URIish uri);
}
