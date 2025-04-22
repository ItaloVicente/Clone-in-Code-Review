package org.eclipse.jgit.lib.internal;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.MessageFormat;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.util.encoders.Hex;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.CredentialItem.CharArrayType;
import org.eclipse.jgit.transport.CredentialItem.InformationalMessage;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

class BouncyCastleGpgKeyPassphrasePrompt implements AutoCloseable {

	private CharArrayType passphrase;

	private CredentialsProvider credentialsProvider;

	public BouncyCastleGpgKeyPassphrasePrompt(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	public void clear() {
		if (passphrase != null) {
			passphrase.clear();
			passphrase = null;
		}
	}

	@Override
	public void close() {
		clear();
	}

	private URIish createURI(Path keyLocation) throws URISyntaxException {
		return new URIish(keyLocation.toUri().toString());
	}

	public char[] getPassphrase(byte[] keyFingerprint
			throws PGPException
			URISyntaxException {
		if (passphrase == null) {
			passphrase = new CharArrayType(JGitText.get().credentialPassphrase
					true);
		}

		if (credentialsProvider == null) {
			throw new PGPException(JGitText.get().gpgNoCredentialsProvider);
		}

		if (passphrase.getValue() == null
				&& !credentialsProvider.get(createURI(keyLocation)
						new InformationalMessage(
								MessageFormat.format(JGitText.get().gpgKeyInfo
										Hex.toHexString(keyFingerprint)))
						passphrase)) {
			throw new CanceledException(JGitText.get().gpgSigningCancelled);
		}
		return passphrase.getValue();
	}

}
