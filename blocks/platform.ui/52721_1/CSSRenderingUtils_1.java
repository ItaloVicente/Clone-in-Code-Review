
		if (handleImage == null) {
			String cssName = (String) toFrame.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);
			boolean containsStretch = cssName != null && cssName.contains("stretch");
			boolean containsGlue = cssName != null && cssName.contains("glue");
			if (!(containsGlue || containsStretch))
				handleImage = JFaceResources.getImage(DRAG_HANDLE);
		}
