		@Override
		public int hashCode() {
			return display.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof CommitFile))
				return false;
			return (this.compareTo((CommitFile)other) == 0);
		}

