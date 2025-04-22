		static Change create(int changeNumber, int patchSetNumber) {
			int subDir = changeNumber % 100;
			return new Change(
					GERRIT_CHANGE_REF_PREFIX
							+ String.format("%02d", Integer.valueOf(subDir)) //$NON-NLS-1$
							+ '/' + changeNumber + '/' + patchSetNumber,
					Integer.valueOf(changeNumber),
					Integer.valueOf(patchSetNumber));
