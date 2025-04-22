
package org.eclipse.jgit.transport;

import java.util.List;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;

public abstract class CredentialsProvider {
	public abstract boolean supports(CredentialItem... items);

	public abstract boolean get(URIish uri
			throws UnsupportedCredentialItem;

	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		return get(uri
	}
}
