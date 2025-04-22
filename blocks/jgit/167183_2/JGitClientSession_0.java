	@Override
	public AuthFuture auth() throws IOException {
		AuthFuture future = super.auth();
		if (earlyError != null) {
			if (log.isDebugEnabled()) {
						+ earlyError);
			}
			future.setException(earlyError);
		} else if (isClosing() || isClosed()) {
			if (log.isDebugEnabled()) {
				log.debug(
			}
			future.setException(new SshException(
					SshdText.get().authenticationOnClosedSession));
		}
		return future;
	}

	@Override
	protected List<String> doReadIdentification(Buffer buffer
			throws IOException {
		try {
			return doReadIdentificationImpl(buffer
		} catch (StreamCorruptedException | RuntimeException e) {
			log.warn(e.getLocalizedMessage()
			earlyError = e;
			throw e;
		}
	}
