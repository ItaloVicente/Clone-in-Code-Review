		} else {
			updateCursorState(relativeToHost);
		}

		if (state != State.OTHER) {
			e.type = SWT.None; // Filter out event as we're interacting with a sash.
		}
	}

	private void onMouseUp(Event e) {
		if (state == State.OTHER) {
			return;
		}
		host.setCapture(false);
		e.type = SWT.None;
		if (!(e.widget instanceof Control)) {
			return;
		}
		Control control = (Control) e.widget;
		if (control.getShell() != host.getShell()) {
			return;
		}
		Point relativeToHost = host.getDisplay().map(control, host, e.x, e.y);
		updateCursorState(relativeToHost);
	}

	private void onMouseDown(Event e) {
		if (state == State.OTHER || e.button != 1 || !(e.widget instanceof Control)) {
			return;
		}
		Control control = (Control) e.widget;
		if (control.getShell() != host.getShell()) {
			return;
		}

		e.type = SWT.None; // Filter out event as we're interacting with a sash.

		Point relativeToHost = host.getDisplay().map(control, host, e.x, e.y);
		sashesToDrag = getSashRects(relativeToHost.x, relativeToHost.y);
		if (sashesToDrag.size() > 0) {
			state = State.DRAGGING;
			host.setCapture(true);
		}
	}

	private void onDragDetect(Event e) {
		if (state == State.OTHER) {
			return;
		}
		e.type = SWT.None; // Filter out event as we're currently interacting with a sash.
	}

	private void onActivate(Event e) {
		if (state == State.OTHER) {
			return;
		}
		e.type = SWT.None; // Filter out event as we're currently interacting with a sash.
	}

	private void updateCursorState(Point relativeToHost) {
		List<SashRect> sashList = getSashRects(relativeToHost.x, relativeToHost.y);
		if (sashList.isEmpty()) {
			if (state != State.OTHER) {
				state = State.OTHER;
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
			}
		} else if (sashList.size() > 1) {
			if (state != State.HOVERING_ALL) {
				state = State.HOVERING_ALL;
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
			}
		} else if (sashList.get(0).container.isHorizontal()) {
			if (state != State.HOVERING_WE) {
				state = State.HOVERING_WE;
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE));
			}
		} else {
			if (state != State.HOVERING_NS) {
				state = State.HOVERING_NS;
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS));
			}
