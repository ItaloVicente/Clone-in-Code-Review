		switch (property) {
		case SWT_SCROLLBAR_BACKGROUND_COLOR:
			Color bgColor = (Color) engine.convert(value, Color.class, styledText.getDisplay());
			styledTextElement.setScrollBarBackgroundColor(bgColor);
			break;
		case SWT_SCROLLBAR_FOREGROUND_COLOR:
			Color fgColor = (Color) engine.convert(value, Color.class, styledText.getDisplay());
			styledTextElement.setScrollBarForegroundColor(fgColor);
			break;
		case SWT_SCROLLBAR_WIDTH:
