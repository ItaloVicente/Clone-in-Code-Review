		} else if (SWT_SCROLLBAR_VERTICAL_VISIBLE.equals(property)) {
			Boolean visible = (Boolean) engine.convert(value, Boolean.class, styledText.getDisplay());
			styledTextElement.setVerticalScrollBarVisible(visible);
		} else if (SWT_SCROLLBAR_HORIZONTAL_VISIBLE.equals(property)) {
			Boolean visible = (Boolean) engine.convert(value, Boolean.class, styledText.getDisplay());
			styledTextElement.setHorizontalScrollBarVisible(visible);
		} else if (SWT_SCROLLBAR_THEMED.equals(property)) {
