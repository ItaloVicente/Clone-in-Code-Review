			if (candidateChange.getPatchSetNumber() != null) {
				String subdir = Integer.toString(
						candidateChange.getChangeNumber().intValue() % 100);
				refText.setText("refs/changes/" + subdir + '/' //$NON-NLS-1$
						+ candidateChange.getChangeNumber() + '/'
						+ candidateChange.getPatchSetNumber());
			} else {
				refText.setText(candidateChange.getChangeNumber().toString());
			}
