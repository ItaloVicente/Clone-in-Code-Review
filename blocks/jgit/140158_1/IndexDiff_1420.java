package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.lib.internal.BouncyCastleGpgSigner;
import org.eclipse.jgit.transport.CredentialsProvider;

public abstract class GpgSigner {

	private static GpgSigner defaultSigner = new BouncyCastleGpgSigner();

	public static GpgSigner getDefault() {
		return defaultSigner;
	}

	public static void setDefault(GpgSigner signer) {
		GpgSigner.defaultSigner = signer;
	}

	public abstract void sign(@NonNull CommitBuilder commit
			@Nullable String gpgSigningKey
			CredentialsProvider credentialsProvider) throws CanceledException;

	public abstract boolean canLocateSigningKey(@Nullable String gpgSigningKey
			@NonNull PersonIdent committer
			CredentialsProvider credentialsProvider) throws CanceledException;

}
