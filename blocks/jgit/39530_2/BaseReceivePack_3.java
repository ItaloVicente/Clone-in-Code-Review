	private enum PushCertificateParsingState {
		notParsing
		expectingVersionNumber
		expectingPusher
		expectingPushee
		expectingNonce
		expectingLF
	}

	private PushCertificateParsingState parsePushCertificate(String rawLine
			PushCertificateParsingState state) throws IOException {
		switch (state) {
		case expectingLF:
			state = PushCertificateParsingState.notParsing;
			return state;
		case expectingNonce:
			if (rawLine.startsWith(nonce)) {
				pushedCertNonce = rawLine.substring(nonce.length());
				state = PushCertificateParsingState.expectingLF;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case expectingPushee:
			if (rawLine.startsWith(pushee)) {
				pushedCertPusher = rawLine.substring(pushee.length());
				state = PushCertificateParsingState.expectingNonce;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case expectingPusher:
			if (rawLine.startsWith(pusher)) {
				pushedCertPusher = rawLine.substring(pusher.length());
				state = PushCertificateParsingState.expectingPushee;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case expectingVersionNumber:
			if (rawLine.startsWith(certVersion))
				version = rawLine.substring(certVersion.length());
				state = PushCertificateParsingState.expectingPusher;
			else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case notParsing:
			return state;
		}
		return state;
	}

