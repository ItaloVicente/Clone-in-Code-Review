
package org.eclipse.jgit.transport;

import java.util.List;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;

public abstract class CredentialsProvider {
	private static volatile CredentialsProvider defaultProvider;

	public static CredentialsProvider getDefault() {
		return defaultProvider;
	}

	public static void setDefault(CredentialsProvider p) {
		defaultProvider = p;
	}

	protected static boolean isAnyNull(CredentialItem... items) {
		for (CredentialItem i : items)
			if (i == null)
				return true;
		return false;
	}

	public abstract boolean isInteractive();

	public abstract boolean supports(CredentialItem... items);

	public abstract boolean get(URIish uri
			throws UnsupportedCredentialItem;

	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		return get(uri
	}

	public void reset(URIish uri) {
	}
}
