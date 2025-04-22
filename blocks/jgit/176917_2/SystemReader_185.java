package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.util.Locale;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.GpgSignatureVerifier.SignatureVerification;
import org.eclipse.jgit.lib.GpgSignatureVerifier.TrustLevel;
import org.eclipse.jgit.lib.PersonIdent;

public final class SignatureUtils {

	private SignatureUtils() {
	}

	public static String toString(SignatureVerification verification
			PersonIdent creator
		StringBuilder result = new StringBuilder();
		PersonIdent dateId = new PersonIdent(creator
				verification.getCreationDate());
		result.append(MessageFormat.format(JGitText.get().verifySignatureMade
				formatter.formatDate(dateId)));
		result.append('\n');
		result.append(MessageFormat.format(
				JGitText.get().verifySignatureKey
				verification.getKeyFingerprint().toUpperCase(Locale.ROOT)));
		result.append('\n');
		if (!StringUtils.isEmptyOrNull(verification.getSigner())) {
			result.append(
					MessageFormat.format(JGitText.get().verifySignatureIssuer
							verification.getSigner()));
			result.append('\n');
		}
		String msg;
		if (verification.getVerified()) {
			if (verification.isExpired()) {
				msg = JGitText.get().verifySignatureExpired;
			} else {
				msg = JGitText.get().verifySignatureGood;
			}
		} else {
			msg = JGitText.get().verifySignatureBad;
		}
		result.append(MessageFormat.format(msg
		if (!TrustLevel.UNKNOWN.equals(verification.getTrustLevel())) {
			result.append(' ' + MessageFormat
					.format(JGitText.get().verifySignatureTrust
							.getTrustLevel().name().toLowerCase(Locale.ROOT)));
		}
		result.append('\n');
		msg = verification.getMessage();
		if (!StringUtils.isEmptyOrNull(msg)) {
			result.append(msg);
			result.append('\n');
		}
		return result.toString();
	}
}
