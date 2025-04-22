	@Override
	protected void checkKeys() throws SshException {
		ServerKeyVerifier serverKeyVerifier = getServerKeyVerifier();
		SocketAddress remoteAddress = getConnectAddress();
		PublicKey serverKey = getKex().getServerKey();
		if (!serverKeyVerifier.verifyServerKey(this, remoteAddress,
				serverKey)) {
			throw new SshException(
					org.apache.sshd.common.SshConstants.SSH2_DISCONNECT_HOST_KEY_NOT_VERIFIABLE,
					SshdText.get().kexServerKeyInvalid);
		}
	}

