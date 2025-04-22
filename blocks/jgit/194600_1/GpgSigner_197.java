			return null;
		}

		private DefaultSigner() {
		}

		public static GpgSigner getDefault() {
			return defaultSigner;
		}

		public static void setDefault(GpgSigner signer) {
			defaultSigner = signer;
