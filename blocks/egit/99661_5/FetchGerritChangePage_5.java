		static Change create(int changeNumber) {
			return new Change(null, Integer.valueOf(changeNumber), null);
		}

		static Change create(int changeNumber, int patchSetNumber) {
			int subDir = changeNumber % 100;
			return new Change(
					GERRIT_CHANGE_REF_PREFIX + subDir + '/' + changeNumber + '/'
							+ patchSetNumber,
					Integer.valueOf(changeNumber),
					Integer.valueOf(patchSetNumber));
		}

