
		if (i % 2 == 0) {
			setAllBackgrounds(JFaceResources.getColorRegistry().get(
					DARK_COLOR_KEY));
		} else {
			setAllBackgrounds(getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
		}
		setAllForegrounds(getDisplay()
				.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
