
	@Override
	public AuthFuture auth() throws IOException {
		if (getUsername() == null) {
			throw new IllegalStateException(
					SshdText.get().sessionWithoutUsername);
		}
		ClientUserAuthService authService = getUserAuthService();
		String serviceName = nextServiceName();
		List<Throwable> errors = null;
		AuthFuture future;
		synchronized (errorLock) {
			future = authService.auth(serviceName);
			if (future == null) {
				throw new IllegalStateException(
								+ serviceName + '\'');
			}
			errors = earlyErrors;
			earlyErrors = null;
			authFuture = future;
		}
		if (errors != null && !errors.isEmpty()) {
			Iterator<Throwable> iter = errors.iterator();
			Throwable first = iter.next();
			iter.forEachRemaining(t -> {
				if (t != first && t != null) {
					first.addSuppressed(t);
				}
			});
			future.setException(first);
			if (log.isDebugEnabled()) {
				log.debug("auth({}) early exception type={}: {}"
						this
						first.getMessage());
			}
			if (first instanceof SshException) {
				throw new SshException(
						((SshException) first).getDisconnectCode()
						first.getMessage()
			}
			throw new IOException(first.getMessage()
		}
		return future;
	}

	@Override
	protected void signalAuthFailure(AuthFuture future
		signalAuthFailure(t);
	}

	private void signalAuthFailure(Throwable t) {
		AuthFuture future = authFuture;
		if (future == null) {
			synchronized (errorLock) {
				if (earlyErrors != null) {
					earlyErrors.add(t);
				}
				future = authFuture;
			}
		}
		if (future != null) {
			future.setException(t);
		}
		if (log.isDebugEnabled()) {
			boolean signalled = future != null && t == future.getException();
			log.debug("signalAuthFailure({}) type={}
					t.getClass().getSimpleName()
					t.getMessage());
		}
	}

	@Override
	public void exceptionCaught(Throwable t) {
		signalAuthFailure(t);
		super.exceptionCaught(t);
	}

	@Override
	protected void preClose() {
		signalAuthFailure(
				new SshException(SshdText.get().authenticationOnClosedSession));
		super.preClose();
	}

	@Override
	protected void handleDisconnect(int code
			Buffer buffer) throws Exception {
		signalAuthFailure(new SshException(code
		super.handleDisconnect(code
	}

	@Override
	protected <C extends Collection<ClientSessionEvent>> C updateCurrentSessionState(
			C newState) {
		if (closeFuture.isClosed()) {
			newState.add(ClientSessionEvent.CLOSED);
		}
			newState.add(ClientSessionEvent.AUTHED);
		}
		if (KexState.DONE.equals(getKexState())) {
			AuthFuture future = authFuture;
			if (future == null || future.isFailure()) {
				newState.add(ClientSessionEvent.WAIT_AUTH);
			}
		}
		return newState;
	}


