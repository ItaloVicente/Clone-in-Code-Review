	private Text createText(Composite parent) {
		Text text = new Text(parent, SWT.SEARCH | SWT.ICON_SEARCH);
		text.setMessage(QuickAccessMessages.QuickAccess_EnterSearch);

		GC gc = new GC(text);
		FontMetrics fm = gc.getFontMetrics();
		int width = text.computeSize(fm.getAverageCharWidth() * text.getMessage().length(),
				SWT.DEFAULT).x + 15 /* some extra space */;
		gc.dispose();

		GridDataFactory.fillDefaults().hint(width, SWT.DEFAULT).applyTo(text);
		return text;
	}

