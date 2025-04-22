	public static Text createSelectableLabel(Composite parent, int style) {
		Text text = new Text(parent, style | SWT.READ_ONLY);
		text.setBackground(text.getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));
		return text;
	}

