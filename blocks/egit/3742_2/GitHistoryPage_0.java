	private static class FilterPath {

		private String path;

		private boolean regularFile;

		public FilterPath(String path, boolean regularFile) {
			super();
			this.path = path;
			this.regularFile = regularFile;
		}

		public String getPath() {
			return path;
		}

		public boolean isRegularFile() {
			return regularFile;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof FilterPath)) {
				return false;
			}
			FilterPath other = (FilterPath) obj;
			if (path == null) {
				return other.path == null;
			}
			return path.equals(other.path);
		}

		@Override
		public int hashCode() {
			if (path != null) {
				return path.hashCode();
			}
			return super.hashCode();
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder("Path: "); //$NON-NLS-1$
			builder.append(getPath());
			builder.append("regular: "); //$NON-NLS-1$
			builder.append(isRegularFile());

			return builder.toString();
		}
	}

