		Button b = new Button(parent, flags);
		b.setData(this);
		b.addListener(SWT.Dispose, getButtonListener());
		b.addListener(SWT.Selection, getButtonListener());
		if (action.getHelpListener() != null) {
			b.addHelpListener(action.getHelpListener());
		}
		widget = b;
