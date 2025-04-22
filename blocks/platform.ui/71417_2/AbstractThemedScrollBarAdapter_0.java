		if (event.type == SWT.MenuDetect || (isWindowsOS && event.type == SWT.MouseDown && event.button != 1)) {
			if (this.fHorizontalScrollHandler.mousePosOverScroll(fScrollable, controlPos)
					|| this.fVerticalScrollHandler.mousePosOverScroll(fScrollable, controlPos)) {
				this.stopEventPropagation(event);
			}
		} else if (event.type == SWT.MouseDown) {
