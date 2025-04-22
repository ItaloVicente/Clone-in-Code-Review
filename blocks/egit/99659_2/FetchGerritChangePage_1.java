		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Change)) {
				return false;
			}
			return compareTo((Change) obj) == 0;
		}

		@Override
		public int hashCode() {
			return Objects.hash(changeNumber, patchSetNumber);
		}

