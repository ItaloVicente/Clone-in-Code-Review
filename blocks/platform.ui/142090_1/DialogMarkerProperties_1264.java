		}

		descriptionText.selectAll();
	}

	protected void updateDialogForNewMarker() {
		if (resource != null && resourceText != null && folderText != null) {
			resourceText.setText(resource.getName());

			IPath path = resource.getFullPath();
			int n = path.segmentCount() - 1; // n is the number of segments in container, not path
			if (n > 0) {
				int len = 0;
				for (int i = 0; i < n; ++i) {
