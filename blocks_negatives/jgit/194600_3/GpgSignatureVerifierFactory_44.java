	private static GpgSignatureVerifierFactory loadDefault() {
		try {
			ServiceLoader<GpgSignatureVerifierFactory> loader = ServiceLoader
					.load(GpgSignatureVerifierFactory.class);
			Iterator<GpgSignatureVerifierFactory> iter = loader.iterator();
			if (iter.hasNext()) {
				return iter.next();
