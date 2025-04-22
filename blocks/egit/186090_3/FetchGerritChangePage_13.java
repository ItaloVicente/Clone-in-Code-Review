		public Change complete(Collection<Change> changes) {
			if (isComplete()) {
				return null;
			}
			return findHighestPatchSet(changes, getChangeNumber());
		}

		private Change findHighestPatchSet(Collection<Change> changes,
				long chgNumber) {
			if (changes == null) {
				return null;
			}
			for (Change fromGerrit : changes) {
				long num = fromGerrit.getChangeNumber();
				if (num < chgNumber) {
					return null; // Doesn't exist
				} else if (chgNumber == num) {
					return fromGerrit;
				}
			}
			return null;
