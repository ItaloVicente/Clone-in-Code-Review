
package org.eclipse.jgit.transport;

import java.util.Arrays;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;

public class UsernamePasswordCredentialsProvider extends CredentialsProvider {
	private String username;

	private char[] password;

	public UsernamePasswordCredentialsProvider(String username
		this(username
	}

	public UsernamePasswordCredentialsProvider(String username
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

	@Override
	public boolean supports(CredentialItem... items) {
		for (CredentialItem i : items) {
			if (i instanceof CredentialItem.Username)
				continue;

			else if (i instanceof CredentialItem.Password)
				continue;

			else
				return false;
		}
		return true;
	}

	@Override
	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		for (CredentialItem i : items) {
			if (i instanceof CredentialItem.Username) {
				((CredentialItem.Username) i).setValue(username);
				continue;
			}
			if (i instanceof CredentialItem.Password) {
				((CredentialItem.Password) i).setValue(password);
				continue;
			}
			if (i instanceof CredentialItem.StringType) {
					((CredentialItem.StringType) i).setValue(new String(
							password));
					continue;
				}
			}
			throw new UnsupportedCredentialItem(uri
		}
		return true;
	}

	public void clear() {
		username = null;

		if (password != null) {
			Arrays.fill(password
			password = null;
		}
	}
}
