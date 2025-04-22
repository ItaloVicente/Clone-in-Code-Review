package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.revwalk.RevObject;

public interface VerificationResult {

	Throwable getException();

	GpgSignatureVerifier.SignatureVerification getVerification();

	RevObject getObject();
}
