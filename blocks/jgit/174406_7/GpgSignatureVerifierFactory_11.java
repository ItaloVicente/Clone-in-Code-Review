package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.Date;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.revwalk.RevObject;

public interface GpgSignatureVerifier {

	@Nullable
	SignatureVerification verifySignature(@NonNull RevObject object
			@NonNull GpgConfig config) throws IOException;

	@NonNull
	String getName();

	void clear();

	interface SignatureVerification {


		@NonNull
		Date getCreationDate();


		String getSigner();

		String getKeyFingerprint();


		String getKeyUser();

		boolean isExpired();

		@NonNull
		TrustLevel getTrustLevel();


		boolean getVerified();

		String getMessage();
	}

	enum TrustLevel {
		UNKNOWN
	}
}
