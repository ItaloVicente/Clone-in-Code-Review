		public String completeId() {
			if (isComplete()) {
				return Long.toString(getChangeNumber()) + '/'
						+ getPatchSetNumber();
			}
			return Long.toString(getChangeNumber());
