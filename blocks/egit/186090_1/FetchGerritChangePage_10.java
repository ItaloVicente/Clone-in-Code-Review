			String ref = refName;
			if (ref == null && patchSetNumber != null) {
				int subDir = (int) (changeNumber % 100);
				ref = GERRIT_CHANGE_REF_PREFIX
						+ String.format("%02d", Integer.valueOf(subDir)) //$NON-NLS-1$
						+ '/' + changeNumber + '/' + patchSetNumber;
			}
			this.refName = ref;
