	private KeyPair load(Path path) throws Exception {
		try (InputStream in = Files.newInputStream(path)) {
			return SecurityUtils
					.loadKeyPairIdentities(null,
							NamedResource.ofName(path.toString()), in, null)
					.iterator().next();
		}
	}

