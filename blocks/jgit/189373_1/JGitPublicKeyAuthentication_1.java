	}

	@Override
	protected Iterator<PublicKeyIdentity> createPublicKeyIterator(
			ClientSession session
			throws Exception {
		agent = getAgent(session);
		return new KeyIterator(session
	}

	private SshAgent getAgent(ClientSession session) throws Exception {
		FactoryManager manager = Objects.requireNonNull(
				session.getFactoryManager()
		SshAgentFactory factory = manager.getAgentFactory();
		if (factory == null) {
			return null;
		}
		return factory.createClient(session
	}

	@Override
	protected void releaseKeys() throws IOException {
		try {
			if (agent != null) {
				try {
					agent.close();
				} finally {
					agent = null;
				}
			}
		} finally {
			super.releaseKeys();
		}
	}

	private class KeyIterator extends UserAuthPublicKeyIterator {

		private Iterable<? extends Map.Entry<PublicKey

		private Collection<PublicKey> identityFiles;

		public KeyIterator(ClientSession session
				SignatureFactoriesManager manager)
				throws Exception {
			super(session
		}

		private List<PublicKey> getExplicitKeys(
				Collection<String> explicitFiles) {
			if (explicitFiles == null) {
				return null;
			}
			return explicitFiles.stream().map(s -> {
				try {
					if (Files.isRegularFile(p
						return AuthorizedKeyEntry.readAuthorizedKeys(p).get(0)
								.resolvePublicKey(null
										PublicKeyEntryResolver.IGNORING);
					}
				} catch (InvalidPathException | IOException
						| GeneralSecurityException e) {
					log.warn(format(SshdText.get().cannotReadPublicKey
				}
				return null;
			}).filter(Objects::nonNull).collect(Collectors.toList());
		}

		@Override
		protected Iterable<KeyAgentIdentity> initializeAgentIdentities(
				ClientSession session) throws IOException {
			if (agent == null) {
				return null;
			}
			agentKeys = agent.getIdentities();
			if (hostConfig != null && hostConfig.isIdentitiesOnly()) {
				identityFiles = getExplicitKeys(hostConfig.getIdentities());
			}
			return () -> new Iterator<>() {

				private final Iterator<? extends Map.Entry<PublicKey
						.iterator();

				private Map.Entry<PublicKey
