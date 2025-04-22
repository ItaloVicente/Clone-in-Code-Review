		if (WEBSITE_CLASS.equals(control.getClass().getName())) {
			control.setBackground(control.getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			control.setForeground(control.getDisplay().getSystemColor(
					SWT.COLOR_LINK_FOREGROUND));
		} else {
			control.setBackground(null);
			control.setForeground(null);
		}
