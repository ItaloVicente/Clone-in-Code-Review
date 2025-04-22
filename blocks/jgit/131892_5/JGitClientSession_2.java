	public void setProxyHandler(StatefulProxyConnector handler) {
		proxyHandler = handler;
	}

	@Override
	protected IoWriteFuture sendIdentification(String ident)
			throws IOException {
		return null;
	}

	@Override
	protected byte[] sendKexInit() throws IOException {
		StatefulProxyConnector proxy = proxyHandler;
		if (proxy != null) {
			try {
				proxy.runWhenDone(() -> {
					sendStartSsh();
					return null;
				});
				return null;
			} catch (Exception e) {
				if (e instanceof IOException) {
					throw (IOException) e;
				}
				throw new IOException(e.getLocalizedMessage()
			}
		} else {
			return sendStartSsh();
		}
	}

	private byte[] sendStartSsh() throws IOException {
		super.sendIdentification(clientVersion);
		return super.sendKexInit();
	}

	@Override
	public void messageReceived(Readable buffer) throws Exception {
		StatefulProxyConnector proxy = proxyHandler;
		if (proxy != null) {
			proxy.messageReceived(getIoSession()
		} else {
			super.messageReceived(buffer);
		}
	}

	@Override
	protected void checkKeys() throws SshException {
		ServerKeyVerifier serverKeyVerifier = getServerKeyVerifier();
		SocketAddress remoteAddress = getConnectAddress();
		PublicKey serverKey = getKex().getServerKey();
		if (!serverKeyVerifier.verifyServerKey(this
				serverKey)) {
			throw new SshException(
					org.apache.sshd.common.SshConstants.SSH2_DISCONNECT_HOST_KEY_NOT_VERIFIABLE
					SshdText.get().kexServerKeyInvalid);
		}
	}

