package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.UnsupportedSigningFormatException;
import org.eclipse.jgit.transport.CredentialsProvider;

public interface GpgObjectSigner {

	void signObject(@NonNull ObjectBuilder object
			@Nullable String gpgSigningKey
			CredentialsProvider credentialsProvider
			throws CanceledException

	public abstract boolean canLocateSigningKey(@Nullable String gpgSigningKey
			@NonNull PersonIdent committer
			CredentialsProvider credentialsProvider
			throws CanceledException

}
