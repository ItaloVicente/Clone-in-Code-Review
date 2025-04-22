package org.eclipse.jgit.niofs.internal;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.URIish;

public class UsernamePasswordCredentialsProvider
		extends org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider {

	public UsernamePasswordCredentialsProvider(final String username
		super(username
	}

	@Override
	public boolean get(final URIish uri
		try {
			return super.get(uri
		} catch (UnsupportedCredentialItem e) {
			for (CredentialItem i : items) {
				if (i instanceof CredentialItem.YesNoType) {
					((CredentialItem.YesNoType) i).setValue(true);
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}
}
