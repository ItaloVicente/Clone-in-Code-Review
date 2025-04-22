package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;

public class ChainingCredentialsProvider extends CredentialsProvider {

	private List<CredentialsProvider> credentialProviders;

	public ChainingCredentialsProvider(CredentialsProvider... providers) {
		this.credentialProviders = new ArrayList<>(
				Arrays.asList(providers));
	}

	@Override
	public boolean isInteractive() {
		for (CredentialsProvider p : credentialProviders)
			if (p.isInteractive())
				return true;
		return false;
	}

	@Override
	public boolean supports(CredentialItem... items) {
		for (CredentialsProvider p : credentialProviders)
			if (p.supports(items))
				return true;
		return false;
	}

	@Override
	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		for (CredentialsProvider p : credentialProviders) {
			if (p.supports(items)) {
				if (!p.get(uri
					if (p.isInteractive()) {
					}
					continue;
				}
				if (isAnyNull(items)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
}
