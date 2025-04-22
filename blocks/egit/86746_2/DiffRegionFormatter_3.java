		@Override
		public boolean equals(Object object) {
			return super.equals(object);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public String toString() {
			return "[FileDiffRange " + (diff == null ? "null" : diff.getPath()) //$NON-NLS-1$ //$NON-NLS-2$
					+ ' ' + super.toString() + ']';
		}
