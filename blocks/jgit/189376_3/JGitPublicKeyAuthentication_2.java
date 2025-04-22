	@Override
	protected PublicKeyIdentity resolveAttemptedPublicKeyIdentity(
			ClientSession session
		PublicKeyIdentity result = getNextKey(session
		currentAlgorithms.clear();
		return result;
	}

	private PublicKeyIdentity getNextKey(ClientSession session
			throws Exception {
		PublicKeyIdentity id = super.resolveAttemptedPublicKeyIdentity(session
				service);
		if (addKeysToAgent && id != null && !(id instanceof KeyAgentIdentity)) {
			KeyPair key = id.getKeyIdentity();
			if (key != null && key.getPublic() != null
					&& key.getPrivate() != null) {
				PublicKey pk = key.getPublic();
				String fingerprint = KeyUtils.getFingerPrint(pk);
				String keyType = KeyUtils.getKeyType(key);
				try {
					if (agentHasKey(pk)) {
						return id;
					}
					if (askBeforeAdding
							&& (session instanceof JGitClientSession)) {
						CredentialsProvider provider = ((JGitClientSession) session)
								.getCredentialsProvider();
						CredentialItem.YesNoType question = new CredentialItem.YesNoType(
								format(SshdText
										.get().pubkeyAuthAddKeyToAgentQuestion
										keyType
						boolean result = provider != null
								&& provider.supports(question)
								&& provider.get(getUri()
						if (!result || !question.getValue()) {
							return id;
						}
					}
					SshAgentKeyConstraint[] rules = constraints;
					if (pk instanceof SecurityKeyPublicKey && !StringUtils.isEmptyOrNull(skProvider)) {
						rules = Arrays.copyOf(rules
						rules[rules.length - 1] =
								new SshAgentKeyConstraint.FidoProviderExtension(skProvider);
					}
					agent.addIdentity(key
				} catch (IOException e) {
					log.error(
							format(SshdText.get().pubkeyAuthAddKeyToAgentError
									keyType
							e);
				}
			}
		}
		return id;
	}

	private boolean agentHasKey(PublicKey pk) throws IOException {
		Iterable<? extends Map.Entry<PublicKey
				.getIdentities();
		if (ids == null) {
			return false;
		}
		Iterator<? extends Map.Entry<PublicKey
		while (iter.hasNext()) {
			if (KeyUtils.compareKeys(iter.next().getKey()
				return true;
			}
		}
		return false;
	}

	private URIish getUri() {
		String userName = hostConfig.getUsername();
		if (!StringUtils.isEmptyOrNull(userName)) {
			uri += userName + '@';
		}
		uri += hostConfig.getHost();
		int port = hostConfig.getPort();
		if (port > 0 && port != SshConstants.SSH_DEFAULT_PORT) {
		}
		try {
			return new URIish(uri);
		} catch (URISyntaxException e) {
			log.error(e.getLocalizedMessage()
		}
		return new URIish();
	}

