		}
		minumumPath = p;
		if (p != null) {
			repositoryToCreate.setText(p.toOSString());
		} else {
			repositoryToCreate.setText(""); //$NON-NLS-1$
		}
		button.setEnabled(p != null);
		repositoryToCreate.setEnabled(p != null);
		dotGitSegment.setEnabled(p != null);
		getContainer().updateButtons();
	}
