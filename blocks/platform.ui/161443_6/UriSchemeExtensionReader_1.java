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
