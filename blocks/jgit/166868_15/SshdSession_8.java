			close(portForward
			close(proxySession
			close(resultSession
			if (e instanceof SshException && ((SshException) e)
					.getDisconnectCode() == SSH2_DISCONNECT_NO_MORE_AUTH_METHODS_AVAILABLE) {
				throw new TransportException(target
						format(SshdText.get().loginDenied
								Integer.toString(port))
						e);
			}
