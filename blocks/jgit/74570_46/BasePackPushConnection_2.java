		if (pushOptions != null && !capablePushOptions) {
			throw new TransportException(uri
					MessageFormat.format(JGitText.get().pushOptionsNotSupported
							pushOptions.toString()));
		}

