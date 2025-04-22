	static PBEParameterSpec java7PBEParameterSpec(byte[] salt
			int iterationCount) {
		return new PBEParameterSpec(salt
	}

	static PBEParameterSpec java8PBEParameterSpec(byte[] salt
			int iterationCount
		try {
			@SuppressWarnings("boxing")
			PBEParameterSpec instance = PBEParameterSpec.class
					.getConstructor(byte[].class
							AlgorithmParameterSpec.class)
					.newInstance(salt
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static double javaVersion() {
	}

	static class ObjectEncryptionJets3tV2 extends WalkEncryption {



		static final int JETS3T_ITERATIONS = 5000;

		static final int JETS3T_KEY_SIZE = 32;

				(byte) 0xA4
				(byte) 0xD6
		};

		static final byte[] ZERO_AES_IV = new byte[16];

		private final String cryptoVer = JETS3T_VERSION;
