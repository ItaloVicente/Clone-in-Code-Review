	protected String checkNonce() {
		if (pushedCertNonce.isEmpty())
			return NONCE_MISSING;
		else if (pushCertNonce.isEmpty())
			return NONCE_UNSOLICITED;
		else if (pushedCertNonce.equals(pushCertNonce))
			return NONCE_OK;


		int idxNonce = pushedCertNonce.indexOf('-');
		int idxPushCert = pushCertNonce.indexOf('-');
		if (idxNonce == -1 || idxPushCert == -1)
			return NONCE_BAD;

		long signedStamp = 0;
		long advertisedStamp = 0;
		try {
			signedStamp = Integer.parseInt(pushedCertNonce.substring(0
			advertisedStamp = Integer.parseInt(
					pushCertNonce.substring(0
		} catch (Exception e) {
			return NONCE_BAD;
		}

		String expect = preparePushCertNonce(
				db.getDirectory().getPath()
				signedStamp);

		if (!expect.equals(pushedCertNonce))
			return NONCE_BAD;

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (certNonceSlopLimit != 0
				&& nonceStampSlop <= certNonceSlopLimit) {
			return NONCE_OK;
		} else {
			return NONCE_SLOP;
		}
	}

	private enum PushCertificateParsingState {
		notParsing
		expectingVersionNumber
		expectingPusher
		expectingPushee
		expectingNonce
		expectingLF
		expectingCommandOrSignature
		expectingSignature
		expectingPushCertEnd
		finished
	}

	private PushCertificateParsingState parsePushCertificate(String rawLine
			PushCertificateParsingState state) throws IOException {
		switch (state) {
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
		case expectingPusher:
			if (rawLine.startsWith(pusher)) {
				pushedCertPusher = rawLine.substring(pusher.length() + 1)
						.replace("\n"
				state = PushCertificateParsingState.expectingPushee;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case expectingPushee:
			if (rawLine.startsWith(pushee)) {
				pushedCertPusher = rawLine.substring(pushee.length() + 1)
						.replace("\n"
				state = PushCertificateParsingState.expectingNonce;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;

		case expectingNonce:
			if (rawLine.startsWith(nonce)) {
				pushedCertNonce = rawLine.substring(nonce.length() + 1)
						.replace("\n"
				state = PushCertificateParsingState.expectingLF;
			} else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			return state;
		case expectingLF:
			state = PushCertificateParsingState.expectingCommandOrSignature;
			return state;
		case expectingCommandOrSignature:
			if (rawLine.length() < 83) {
				final String m = JGitText.get().errorInvalidProtocolWantedOldNewRef;
				sendError(m);
				throw new PackProtocolException(m);
			}

			final ObjectId oldId = ObjectId.fromString(rawLine.substring(0
			final ObjectId newId = ObjectId.fromString(rawLine.substring(41
			final String name = rawLine.substring(82);
			final ReceiveCommand cmd = new ReceiveCommand(oldId
			if (name.equals(Constants.HEAD)) {
				cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
			} else {
				cmd.setRef(refs.get(cmd.getRefName()));
			}
			commands.add(cmd);
			break;
		case expectingSignature:
			break;
		case expectingPushCertEnd:
			if (rawLine.equals(certEnd))
				state = PushCertificateParsingState.finished;
			else {
				final IOException p;
				p = new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
				throw p;
			}
			break;
		case finished:
			break;
		}
		return state;
	}

	protected void recvCommandsWithPushCertificate() throws IOException {
		PushCertificateParsingState parsingPushCertificateState =
				PushCertificateParsingState.expectingVersionNumber;
		StringBuilder pushedCommandsBuilder = new StringBuilder();
		while (parsingPushCertificateState
				!= PushCertificateParsingState.finished) {
			String line;
			try {
				line = pckIn.readStringRaw();
			} catch (EOFException eof) {
				if (commands.isEmpty())
					return;
				throw eof;
			}
			pushedCommandsBuilder.append(line);
			parsingPushCertificateState = parsePushCertificate(
					line
		}
		pushedCommandsBuilder.toString();
		pushCertNonceStatus = checkNonce();
		verifySignedPushCommands();
	}

