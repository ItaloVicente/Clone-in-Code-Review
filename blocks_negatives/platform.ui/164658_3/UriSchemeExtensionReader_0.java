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

		@Override
		public boolean equals(Object o) {
			if (o.getClass() != this.getClass()) {
				return false;
			}
			Scheme other = (Scheme) o;
			return Objects.equals(this.uriScheme, other.uriScheme)
					&& Objects.equals(this.uriSchemeDescription, other.uriSchemeDescription);
		}

		@Override
		public int hashCode() {
			return Objects.hash(uriScheme, uriSchemeDescription);
		}
	}

