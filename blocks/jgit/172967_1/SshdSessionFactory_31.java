			if (e instanceof TransportException) {
				throw (TransportException) e;
			}
			Throwable cause = e;
			if (e instanceof SshException && e
					.getCause() instanceof AuthenticationCanceledException) {
				cause = e.getCause();
			}
			throw new TransportException(uri
