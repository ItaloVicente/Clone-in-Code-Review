		Image image;
		if (diff.getPath() != null) {
			image = (Image) resourceManager
					.get(UIUtils.getEditorImage(diff.getPath()));
		} else {
			image = (Image) resourceManager.get(UIUtils.DEFAULT_FILE_IMG);
