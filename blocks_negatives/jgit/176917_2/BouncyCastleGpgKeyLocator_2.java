		/*
		 * this is somewhat brute-force but there doesn't seem to be another
		 * way; we have to walk all private key files we find and try to open
		 * them
		 */

		PGPDigestCalculatorProvider calculatorProvider = new JcaPGPDigestCalculatorProviderBuilder()
				.build();

		try (Stream<Path> keyFiles = Files.walk(USER_SECRET_KEY_DIR)) {
			List<Path> allPaths = keyFiles.filter(Files::isRegularFile)
					.collect(Collectors.toCollection(ArrayList::new));
			if (allPaths.isEmpty()) {
				return null;
