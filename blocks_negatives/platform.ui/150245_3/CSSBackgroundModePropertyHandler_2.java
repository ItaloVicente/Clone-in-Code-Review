		if (control instanceof Composite) {
			Composite composite = (Composite) control;
			String stringValue = value.getCssText().toLowerCase();
			if ("default".equalsIgnoreCase(stringValue)) {
				composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
			} else if ("force".equalsIgnoreCase(stringValue)) {
				composite.setBackgroundMode(SWT.INHERIT_FORCE);
			} else if ("none".equalsIgnoreCase(stringValue)) {
				composite.setBackgroundMode(SWT.INHERIT_NONE);
			}
