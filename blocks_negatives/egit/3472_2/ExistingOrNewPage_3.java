		}
		minumumPath = p;
		if (p != null) {
			repositoryToCreate.setText(p.toOSString());
		} else {
		}
		button.setEnabled(p != null);
		repositoryToCreate.setEnabled(p != null);
		dotGitSegment.setEnabled(p != null);
		getContainer().updateButtons();
	}
