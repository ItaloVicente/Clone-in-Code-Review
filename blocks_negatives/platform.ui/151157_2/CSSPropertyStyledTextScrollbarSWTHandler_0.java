		if (SWT_SCROLLBAR_BACKGROUND_COLOR.equals(property)) {
			Color newColor = (Color) engine.convert(value, Color.class, styledText.getDisplay());
			styledTextElement.setScrollBarBackgroundColor(newColor);
		} else if (SWT_SCROLLBAR_FOREGROUND_COLOR.equals(property)) {
			Color newColor = (Color) engine.convert(value, Color.class, styledText.getDisplay());
			styledTextElement.setScrollBarForegroundColor(newColor);
		} else if (SWT_SCROLLBAR_WIDTH.equals(property)) {
