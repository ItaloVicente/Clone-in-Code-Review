package org.eclipse.jgit.transport;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.NetRC.NetRCEntry;

public class NetRCCredentialsProvider extends CredentialsProvider {

	NetRC netrc = new NetRC();

	public NetRCCredentialsProvider() {
	}

	public static void install() {
		CredentialsProvider.setDefault(new NetRCCredentialsProvider());
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
		NetRCEntry cc = netrc.getEntry(uri.getHost());

		if (cc == null)
			return false;

		for (CredentialItem i : items) {
			if (i instanceof CredentialItem.Username) {
				((CredentialItem.Username) i).setValue(cc.login);
				continue;
			}
			if (i instanceof CredentialItem.Password) {
				((CredentialItem.Password) i).setValue(cc.password);
				continue;
			}
			if (i instanceof CredentialItem.StringType) {
					((CredentialItem.StringType) i).setValue(new String(
							cc.password));
					continue;
				}
			}
			throw new UnsupportedCredentialItem(uri
		}
		return true;
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

}
