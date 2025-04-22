		if (toolbar instanceof ToolBarManager) {
			Control control = ((ToolBarManager) toolbar).getControl();
			if (control != null) {
				headerFocusTracker.addToFocusTracking(control);
			}
		}
