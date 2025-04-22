		String targetName = getProposedTargetName(myBaseRef);
		if (targetName != null) {
			nameText.setText(targetName);
			nameText.selectAll();
		} else
			setPageComplete(false);
