		if (workingCopy != null) {
			workingCopy.removeActivityManagerListener((CategoryLabelProvider) categoryViewer
					.getLabelProvider());
		}
		super.dispose();
	}
