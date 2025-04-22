						withAuthLog(message
			} else if (e instanceof SshException && e
					.getCause() instanceof AuthenticationCanceledException) {
				String message = e.getCause().getMessage();
				throw new TransportException(target
						withAuthLog(message
