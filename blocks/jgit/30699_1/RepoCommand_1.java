
		String getPathWithSlash() {
				return path;
			else
		}

		boolean isParentTo(Project that) {
			return that.getPathWithSlash().startsWith(this.getPathWithSlash());
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Project) {
				Project that = (Project) o;
				return this.getPathWithSlash().equals(that.getPathWithSlash());
			}
			return false;
		}

		@Override
		public int compareTo(Project that) {
			return this.getPathWithSlash().compareTo(that.getPathWithSlash());
		}
