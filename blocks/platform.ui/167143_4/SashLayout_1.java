	private void onMouseMove(Event e) {
		if (!(e.widget instanceof Control)) {
			return;
		}
		Control control = (Control) e.widget;
		if (control.getShell() != host.getShell()) {
			return;
		}
		Point relativeToHost = host.getDisplay().map(control, host, e.x, e.y);
		if (state == State.DRAGGING) {
