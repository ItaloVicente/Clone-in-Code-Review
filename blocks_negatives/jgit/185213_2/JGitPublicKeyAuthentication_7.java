		String name = getName();
		if (log.isDebugEnabled()) {
			log.debug(
					"sendAuthDataRequest({})[{}] send SSH_MSG_USERAUTH_REQUEST request {} type={} - fingerprint={}", //$NON-NLS-1$
					session, service, name, currentAlgorithm,
					KeyUtils.getFingerPrint(key));
		}

		chosenAlgorithm = currentAlgorithm;
		Buffer buffer = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		buffer.putString(session.getUsername());
		buffer.putString(service);
		buffer.putString(name);
		buffer.putBoolean(false);
		buffer.putString(currentAlgorithm);
		buffer.putPublicKey(key);
		session.writePacket(buffer);
		return true;
	}

	@Override
	protected boolean processAuthDataRequest(ClientSession session,
			String service, Buffer buffer) throws Exception {
		String name = getName();
		int cmd = buffer.getUByte();
		if (cmd != SshConstants.SSH_MSG_USERAUTH_PK_OK) {
			throw new IllegalStateException(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongCommand,
					SshConstants.getCommandMessageName(cmd),
					session.getConnectAddress(), session.getServerVersion()));
		}
		PublicKey key;
		try {
			key = current.getPublicKey();
			throw new RuntimeSshException(e);
		}
		String rspKeyAlgorithm = buffer.getString();
		PublicKey rspKey = buffer.getPublicKey();
		if (log.isDebugEnabled()) {
			log.debug(
					"processAuthDataRequest({})[{}][{}] SSH_MSG_USERAUTH_PK_OK type={}, fingerprint={}", //$NON-NLS-1$
					session, service, name, rspKeyAlgorithm,
					KeyUtils.getFingerPrint(rspKey));
		}
		if (!KeyUtils.compareKeys(rspKey, key)) {
			throw new InvalidKeySpecException(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongKey,
					KeyUtils.getFingerPrint(key),
					KeyUtils.getFingerPrint(rspKey),
					session.getConnectAddress(), session.getServerVersion()));
		}
		if (!chosenAlgorithm.equalsIgnoreCase(rspKeyAlgorithm)) {
			log.warn(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongSignatureAlgorithm,
					chosenAlgorithm, rspKeyAlgorithm, session.getConnectAddress(),
					session.getServerVersion()));
		}
		String username = session.getUsername();
		Buffer out = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		out.putString(username);
		out.putString(service);
		out.putString(name);
		out.putBoolean(true);
		out.putString(chosenAlgorithm);
		out.putPublicKey(key);
		if (log.isDebugEnabled()) {
			log.debug(
					"processAuthDataRequest({})[{}][{}]: signing with algorithm {}", //$NON-NLS-1$
					session, service, name, chosenAlgorithm);
		}
		appendSignature(session, service, name, username, chosenAlgorithm, key,
				out);
		session.writePacket(out);
		return true;
	}

	@Override
	protected void releaseKeys() throws IOException {
		currentAlgorithms.clear();
		current = null;
		chosenAlgorithm = null;
		super.releaseKeys();
