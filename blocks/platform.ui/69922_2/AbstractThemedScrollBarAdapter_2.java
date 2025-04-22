
		} else if (event.type == SWT.MenuDetect) {
			if (this.fHorizontalScrollHandler.mousePosOverScroll(fScrollable, controlPos)
					|| this.fVerticalScrollHandler.mousePosOverScroll(fScrollable, controlPos)) {
				this.stopEventPropagation(event);
			}
