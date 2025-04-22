
		if (handleImage == null) {
			String cssId = (String) toFrame.getData(CSSSWTConstants.CSS_ID_KEY);
			String cssName = (String) toFrame.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);
			boolean containsStretch = cssName != null && cssName.contains("stretch");
			boolean containsGlue = cssName != null && cssName.contains("glue");
			boolean isStatusBar = "org-eclipse-ui-StatusLine".equals(cssId);
			if (!(containsGlue || containsStretch || isStatusBar))
				handleImage = JFaceResources.getImage(DRAG_HANDLE);
		}

