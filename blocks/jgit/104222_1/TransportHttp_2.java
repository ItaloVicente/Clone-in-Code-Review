	private static class CredentialItems {
		CredentialItem.InformationalMessage message;

		CredentialItem.YesNoType now;

		CredentialItem.YesNoType forRepo;

		CredentialItem.YesNoType always;

		public CredentialItem[] items() {
			if (forRepo == null) {
				return new CredentialItem[] { message
			} else {
				return new CredentialItem[] { message
			}
		}
	}

	private void handleSslFailure(Throwable e) throws TransportException {
		if (sslFailure || !trustInsecureSslConnection(e.getCause())) {
			throw new TransportException(uri
					MessageFormat.format(
							JGitText.get().sslFailureExceptionMessage
							currentUri.setPass(null))
					e);
		}
		sslFailure = true;
	}

	private boolean trustInsecureSslConnection(Throwable cause) {
		if (cause instanceof CertificateException
				|| cause instanceof CertPathBuilderException
				|| cause instanceof CertPathValidatorException) {
			CredentialsProvider provider = getCredentialsProvider();
			if (provider != null) {
				CredentialItems trust = constructSslTrustItems(cause);
				CredentialItem[] items = trust.items();
				if (provider.supports(items)) {
					boolean answered = provider.get(uri
					if (answered) {
						boolean trustNow = trust.now.getValue();
						boolean trustLocal = trust.forRepo != null
								&& trust.forRepo.getValue();
						boolean trustAlways = trust.always.getValue();
						if (trustNow || trustLocal || trustAlways) {
							sslVerify = false;
							if (trustAlways) {
								updateSslVerify(SystemReader.getInstance()
										.openUserConfig(null
										false);
							} else if (trustLocal) {
								updateSslVerify(local.getConfig()
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private CredentialItems constructSslTrustItems(Throwable cause) {
		CredentialItems items = new CredentialItems();
		String info = MessageFormat.format(JGitText.get().sslFailureInfo
				currentUri.setPass(null));
		String sslMessage = cause.getLocalizedMessage();
		if (sslMessage == null) {
			sslMessage = cause.toString();
		}
		sslMessage = MessageFormat.format(JGitText.get().sslFailureCause
				sslMessage);
		items.message = new CredentialItem.InformationalMessage(info + '\n'
				+ sslMessage + '\n'
				+ JGitText.get().sslFailureTrustExplanation);
		items.now = new CredentialItem.YesNoType(JGitText.get().sslTrustNow);
		if (local != null) {
			items.forRepo = new CredentialItem.YesNoType(
					MessageFormat.format(JGitText.get().sslTrustForRepo
					local.getDirectory()));
		}
		items.always = new CredentialItem.YesNoType(
				JGitText.get().sslTrustAlways);
		return items;
	}

	private void updateSslVerify(StoredConfig config
		int port = uri.getPort();
		if (port > 0) {
			uriPattern += ':' + port;
		}
		config.setBoolean(HttpConfig.HTTP
				HttpConfig.SSL_VERIFY_KEY
		try {
			config.save();
		} catch (IOException e) {
			LOG.error(JGitText.get().sslVerifyCannotSave
		}
	}

