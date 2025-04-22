	private static class Scheme implements IScheme {

		private String uriScheme;
		private String uriSchemeDescription;

		public Scheme(String uriScheme, String uriSchemeDescription) {
			super();
			this.uriScheme = uriScheme;
			this.uriSchemeDescription = uriSchemeDescription;
		}

		@Override
		public String getName() {
			return uriScheme;
		}

		@Override
		public String getDescription() {
			return uriSchemeDescription;
		}

	}
