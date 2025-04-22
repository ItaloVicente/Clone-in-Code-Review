			return null;
		}

		private DefaultFactory() {
		}

		public static GpgSignatureVerifierFactory getDefault() {
			return defaultFactory;
		}

		public static void setDefault(GpgSignatureVerifierFactory factory) {
			defaultFactory = factory;
