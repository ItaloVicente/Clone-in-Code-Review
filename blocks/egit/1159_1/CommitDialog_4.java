		if (!allowToChangeSelection) {
			amendingButton.setSelection(false);
			amendingButton.setEnabled(false);
			showUntrackedButton.setSelection(false);
			showUntrackedButton.setEnabled(false);

			filesViewer.addCheckStateListener(new ICheckStateListener() {
