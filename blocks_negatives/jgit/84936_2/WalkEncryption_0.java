	static PBEParameterSpec java7PBEParameterSpec(byte[] salt,
			int iterationCount) {
		return new PBEParameterSpec(salt, iterationCount);
	}

	static PBEParameterSpec java8PBEParameterSpec(byte[] salt,
			int iterationCount, AlgorithmParameterSpec paramSpec) {
		try {
			@SuppressWarnings("boxing")
			PBEParameterSpec instance = PBEParameterSpec.class
					.getConstructor(byte[].class, int.class,
							AlgorithmParameterSpec.class)
					.newInstance(salt, iterationCount, paramSpec);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static double javaVersion() {
	}

