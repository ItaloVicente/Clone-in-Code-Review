package org.eclipse.jgit.pgm.internal;

import java.io.IOException;

import org.eclipse.jgit.lib.GpgSignatureVerifier.SignatureVerification;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.GitDateFormatter;
import org.eclipse.jgit.util.SignatureUtils;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;

public final class VerificationUtils {

	private VerificationUtils() {
	}

	public static void writeVerification(ThrowingPrintWriter out
			SignatureVerification verification
			PersonIdent creator) throws IOException {
		String[] text = SignatureUtils
				.toString(verification
						new GitDateFormatter(GitDateFormatter.Format.LOCALE))
		for (String line : text) {
			out.print(name);
			out.println(line);
		}
	}
}
