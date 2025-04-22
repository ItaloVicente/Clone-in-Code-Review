		Composite composite = (Composite) control;
		String stringValue = value.getCssText().toLowerCase();

		switch (stringValue) {
		case "default":
			composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
			break;
		case "force":
			composite.setBackgroundMode(SWT.INHERIT_FORCE);
			break;
		case "none":
			composite.setBackgroundMode(SWT.INHERIT_NONE);
			break;
