	@Override
	protected boolean processAuthDataRequest(ClientSession session
			String service
		String name = getName();
		int cmd = buffer.getUByte();
		if (cmd != SshConstants.SSH_MSG_USERAUTH_PK_OK) {
			throw new IllegalStateException(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongCommand
					SshConstants.getCommandMessageName(cmd)
					session.getConnectAddress()
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
					"processAuthDataRequest({})[{}][{}] SSH_MSG_USERAUTH_PK_OK type={}
					session
					KeyUtils.getFingerPrint(rspKey));
		}
		if (!KeyUtils.compareKeys(rspKey
			throw new InvalidKeySpecException(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongKey
					KeyUtils.getFingerPrint(key)
					KeyUtils.getFingerPrint(rspKey)
					session.getConnectAddress()
		}
		if (!chosenAlgorithm.equalsIgnoreCase(rspKeyAlgorithm)) {
			log.warn(MessageFormat.format(
					SshdText.get().pubkeyAuthWrongSignatureAlgorithm
					chosenAlgorithm
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
					"processAuthDataRequest({})[{}][{}]: signing with algorithm {}"
					session
		}
		appendSignature(session
				out);
		session.writePacket(out);
		return true;
	}

