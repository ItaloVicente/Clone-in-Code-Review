	private Text createText(Composite comp, String text) {
		Text t = new Text(comp, SWT.READ_ONLY | SWT.WRAP);
		t.setText(text);
		t.setFont(font);
		return t;
	}

