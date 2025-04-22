				if (e.type == SWT.DefaultSelection) {
					SelectionEvent event = new SelectionEvent(e);
					fireDefaultSelectionEvent(event);
					if (CURRENT_METHOD == DOUBLE_CLICK) {
						fireOpenEvent(event);
					} else {
						if (enterKeyDown) {
							fireOpenEvent(event);
							enterKeyDown = false;
							defaultSelectionPendent = null;
						} else {
							defaultSelectionPendent = event;
						}
					}
					return;
				}

				switch (e.type) {
				case SWT.MouseEnter:
				case SWT.MouseExit:
					mouseUpEvent = null;
					mouseMoveEvent = null;
					selectionPendent = null;
					break;
				case SWT.MouseMove:
					if ((CURRENT_METHOD & SELECT_ON_HOVER) == 0) {
