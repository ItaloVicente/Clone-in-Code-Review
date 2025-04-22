		static Change create(int changeNumber) {
			return new Change(null, Integer.valueOf(changeNumber), null);
		}

		static Change create(int changeNumber, int patchSetNumber) {
			int subDir = changeNumber % 100;
			return new Change(
					"refs/changes/" + subDir + '/' + changeNumber + '/' //$NON-NLS-1$
							+ patchSetNumber,
					Integer.valueOf(changeNumber),
					Integer.valueOf(patchSetNumber));
		}

