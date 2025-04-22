				.doPrivileged(new PrivilegedAction<PasswordAuthentication>() {

					@Override
					public PasswordAuthentication run() {
						return Authenticator.requestPasswordAuthentication(
								proxy.getHostString(), proxy.getAddress(),
								proxy.getPort(), SshConstants.SSH_SCHEME,
								SshdText.get().proxyPasswordPrompt, "Basic", //$NON-NLS-1$
								null, RequestorType.PROXY);
					}
				});
