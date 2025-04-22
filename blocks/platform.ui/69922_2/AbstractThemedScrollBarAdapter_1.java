
		Point displayPos;
		if (event.type == SWT.MenuDetect) {
			displayPos = new Point(event.x, event.y);
		} else {
			displayPos = control.toDisplay(event.x, event.y);
		}
