			if (candidateChange.patchSetNumber != null) {
				String subdir = Integer.toString(
						Integer.parseInt(candidateChange.changeNumber) % 100);
				refText.setText("refs/changes/" + subdir + '/' //$NON-NLS-1$
						+ candidateChange.changeNumber + '/'
						+ candidateChange.patchSetNumber);
			} else {
				refText.setText(candidateChange.changeNumber);
			}
