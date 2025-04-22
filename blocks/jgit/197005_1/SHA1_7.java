	private static Sha1Implementation getImplementation() {
		String fromSystemProperty = System
		if (fromSystemProperty == null) {
			return SHA1_IMPLEMENTATION;
		}
		if (fromSystemProperty
				.equalsIgnoreCase(Sha1Implementation.JAVA.name())) {
			return Sha1Implementation.JAVA;
		}
		if (fromSystemProperty
				.equalsIgnoreCase(Sha1Implementation.JDKNATIVE.name())) {
			return Sha1Implementation.JDKNATIVE;
		}
		return SHA1_IMPLEMENTATION;
