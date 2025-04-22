	private static GpgSigner defaultSigner = loadGpgSigner();

	private static GpgSigner loadGpgSigner() {
		ServiceLoader<GpgSigner> loader = ServiceLoader.load(GpgSigner.class);
		Iterator<GpgSigner> iter = loader.iterator();
		if (iter.hasNext()) {
			return iter.next();
		}
		return null;
	}
