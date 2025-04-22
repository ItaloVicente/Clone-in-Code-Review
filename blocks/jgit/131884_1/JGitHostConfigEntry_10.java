package org.eclipse.jgit.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class IdentityPasswordProvider implements FilePasswordProvider {

	private CredentialsProvider provider;

	public IdentityPasswordProvider(CredentialsProvider provider) {
		this.provider = provider;
	}

	protected URIish toUri(String resourceKey) {
		try {
			return new URIish(resourceKey);
		} catch (URISyntaxException e) {
		}
	}

	@Override
	public String getPassword(String resourceKey) throws IOException {
		if (provider == null) {
			return null;
		}
		URIish file = toUri(resourceKey);
		List<CredentialItem> items = new ArrayList<>(2);
		items.add(new CredentialItem.InformationalMessage(
				format(SshdText.get().keyEncryptedMsg
		CredentialItem.Password password = new CredentialItem.Password(
				SshdText.get().keyEncryptedPrompt);
		items.add(password);
		try {
			provider.get(file
			char[] pass = password.getValue();
			if (pass == null) {
				throw new CancellationException(
						SshdText.get().authenticationCanceled);
			}
			return new String(pass);
		} finally {
			password.clear();
		}
	}

}
