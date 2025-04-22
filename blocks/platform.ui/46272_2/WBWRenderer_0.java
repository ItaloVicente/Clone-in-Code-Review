
		if (wbwModel.getLabel() != null)
			wbwShell.setText(wbwModel.getLocalizedLabel());

		if (wbwModel.getIconURI() != null && wbwModel.getIconURI().length() > 0) {
			wbwShell.setImage(getImage(wbwModel));
		} else {
			wbwShell.setImages(Window.getDefaultImages());
		}

		return newWidget;
	}

	void installSaveHandler(final MApplication application) {
