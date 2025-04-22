		HeaderText text = new HeaderText(form.getForm(), title, commitName);
		Control textControl = text.getControl();
		if (textControl != null) {
			headerFocusTracker.addToFocusTracking(textControl);
		}
		form.setToolTipText(commitName);
