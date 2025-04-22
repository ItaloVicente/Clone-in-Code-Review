	}

	/**
	 * Used to signal the end of any layout changes when the mouse is released.
	 *
	 * @param e the mouse event
	 */
	private void onMouseUp(Event e) {
		if (state == State.OTHER) {
			return;
		}
		host.setCapture(false);
		state = State.OTHER;
		e.type = SWT.None;
	}

	/**
	 * Used to signal and prepare the start of layout changes. The
	 * {@link #onMouseMove(Event)} method will then keep track of the actual
	 * dragging and perform layout updates.
	 *
	 * @param e the mouse event
	 */
	private void onMouseDown(Event e) {
		if (state == State.OTHER || e.button != 1 || !(e.widget instanceof Control)) {
			return;
		}
		Control control = (Control) e.widget;
		if (control.getShell() != host.getShell()) {
			return;
		}


		Point relativeToHost = host.getDisplay().map(control, host, e.x, e.y);
		sashesToDrag = getSashRects(relativeToHost.x, relativeToHost.y);
		if (sashesToDrag.size() > 0) {
			state = State.DRAGGING;
			host.setCapture(true);
		}
	}

	/**
	 * Used to filter out drag events when there is an ongoing interaction with a
	 * sash, as layout updates through dragging are handled via the
	 * {@link #onMouseMove(Event)} method.
	 *
	 * @param e the mouse event
	 */
	private void onDragDetect(Event e) {
		if (state == State.OTHER) {
			return;
		}
	}

	/**
	 * Used to filter out activate events when there is an ongoing interaction with
	 * a sash. Without this, starting to update the sash layout by clicking with the
	 * mouse ({@link #onMouseDown(Event)}) would change the active part.
	 *
	 * @param e the mouse event
	 */
	private void onActivate(Event e) {
		if (state == State.OTHER) {
			return;
		}
	}

	@Override
	public int getSashWidth() {
		return sashWidth;
	}

	@Override
	public void setSashWidth(int sashWidth) {
		this.sashWidth = sashWidth;
