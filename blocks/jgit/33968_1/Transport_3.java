package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;

public class ChainingCredentialsProvider extends CredentialsProvider {

	private List<CredentialsProvider> credentialProviders;

	public ChainingCredentialsProvider(CredentialsProvider... providers) {
		this.credentialProviders = new ArrayList<CredentialsProvider>(
				Arrays.asList(providers));
		for (CredentialsProvider p : providers)
			credentialProviders.add(p);
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
				p.get(uri
				if (isAnyNull(items))
					continue;
				return true;
			}
		}
		return false;
	}

	private boolean isAnyNull(CredentialItem... items) {
		for (CredentialItem i : items)
			if (i == null)
				return true;
		return false;
	}
}
