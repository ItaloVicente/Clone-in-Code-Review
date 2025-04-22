			mi.setData(this);
			mi.addListener(SWT.Dispose, getMenuItemListener());
			mi.addListener(SWT.Selection, getMenuItemListener());
			if (action.getHelpListener() != null) {
				mi.addHelpListener(action.getHelpListener());
			}
