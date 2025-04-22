	/**
	 * Simple pojo holding information about an available URI scheme
	 *
	 */
	public static class Scheme {

		private String uriScheme;
		private String uriSchemeDescription;

		/**
		 * Returns an instance of Scheme
		 *
		 * @param uriScheme            The URI scheme
		 * @param uriSchemeDescription The description of the URI scheme
		 */
		public Scheme(String uriScheme, String uriSchemeDescription) {
			super();
			this.uriScheme = uriScheme;
			this.uriSchemeDescription = uriSchemeDescription;
		}

		/**
		 *
		 * @return The URI scheme
		 */
		public String getUriScheme() {
			return uriScheme;
		}

		/**
		 *
		 * @return The description of the URI scheme
		 */
		public String getUriSchemeDescription() {
			return uriSchemeDescription;
		}

	}

