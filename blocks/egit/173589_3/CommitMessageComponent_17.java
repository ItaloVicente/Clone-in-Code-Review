package org.eclipse.egit.ui.internal.credentials;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.lib.GpgSigner;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public final class SignatureUtils {

	private SignatureUtils() {
	}

	public static boolean checkSigningKey(String signingKey,
			@NonNull PersonIdent personIdent) {
		return checkSigningKey(GpgSigner.getDefault(), signingKey, personIdent);
	}

	public static boolean checkSigningKey(GpgSigner signer, String signingKey,
			@NonNull PersonIdent personIdent) {
		if (signer != null) {
			try {
				return signer.canLocateSigningKey(signingKey, personIdent,
						new CredentialsProvider() {

							@Override
							public boolean supports(CredentialItem... items) {
								return true;
							}

							@Override
							public boolean isInteractive() {
								return false;
							}

							@Override
							public boolean get(URIish uri,
									CredentialItem... items)
									throws UnsupportedCredentialItem {
								return false;
							}
						});
			} catch (CanceledException e) {
				return true;
			}
		}
		return false;
	}
}
