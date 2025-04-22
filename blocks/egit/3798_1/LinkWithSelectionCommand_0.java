		Widget w = evt.widget;
		final boolean selected;
		if (w instanceof ToolItem) {
			ToolItem item = (ToolItem) w;
			selected = item.getSelection();
		} else if (w instanceof MenuItem) {
			MenuItem item = (MenuItem) w;
			selected = item.getSelection();
		} else
			throw new IllegalStateException(
					"unexpected type: " + w.getClass().toString()); //$NON-NLS-1$
