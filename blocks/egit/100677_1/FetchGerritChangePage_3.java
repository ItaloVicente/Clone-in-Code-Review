	private Change findHighestPatchSet(Collection<Change> changes,
			int changeNumber) {
		for (Change fromGerrit : changes) {
			int num = fromGerrit.getChangeNumber().intValue();
			if (num < changeNumber) {
				return null; // Doesn't exist
			} else if (changeNumber == num) {
				return fromGerrit;
			}
		}
		return null;
	}

