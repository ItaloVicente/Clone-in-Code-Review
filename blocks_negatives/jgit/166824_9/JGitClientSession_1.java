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
			log.debug("signalAuthFailure({}) type={}, signalled={}: {}", this, //$NON-NLS-1$
					t.getClass().getSimpleName(), Boolean.valueOf(signalled),
					t.getMessage());
		}
	}

