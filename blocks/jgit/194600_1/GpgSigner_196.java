		private static GpgSigner loadGpgSigner() {
			try {
				ServiceLoader<GpgSigner> loader = ServiceLoader
						.load(GpgSigner.class);
				Iterator<GpgSigner> iter = loader.iterator();
				if (iter.hasNext()) {
					return iter.next();
				}
			} catch (ServiceConfigurationError e) {
				LOG.error(e.getMessage()
