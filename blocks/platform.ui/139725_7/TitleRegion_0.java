		titleText = new Text(this, SWT.WRAP);
		titleText.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_IBEAM));
		titleText.setEditable(false);
		titleText.addFocusListener(FocusListener.focusLostAdapter(e -> titleText.setSelection(0)));
		titleText.setVisible(false);
		currentTitleControl = titleLabel;
