		StyledText text = notesText.getTextWidget();
		text.setEditable(false);
		text.setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		text.setForeground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));
		text.setBackground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND));
