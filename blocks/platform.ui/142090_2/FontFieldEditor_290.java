	}

	private void updateFont(FontData font[]) {
		FontData[] bestFont = JFaceResources.getFontRegistry().filterData(
				font, valueControl.getDisplay());

		if (bestFont == null) {
