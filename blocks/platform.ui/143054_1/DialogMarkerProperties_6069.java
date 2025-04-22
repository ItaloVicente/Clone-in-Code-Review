	protected void updateDialogFromMarker() {
		if (marker == null) {
			updateDialogForNewMarker();
			return;
		}
		descriptionText.setText(Util.getProperty(IMarker.MESSAGE, marker));
		if (creationTime != null) {
