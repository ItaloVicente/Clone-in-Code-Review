		Control parentControl = null;
		if (parent instanceof Control) {
			parentControl = (Control) parent;
		} else if (parent instanceof Viewer) {
			parentControl = ((Viewer) parent).getControl();
		} else {
