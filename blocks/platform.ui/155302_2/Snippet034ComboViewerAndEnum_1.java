		MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown"), OTHER("Other");

		private String displayName;

		private Gender(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return displayName;
		}
