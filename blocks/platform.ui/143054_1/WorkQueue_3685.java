			paintListenerAttached = false;
			d.removeFilter(SWT.Paint, this);
			doUpdate();
		}
	};

	public WorkQueue(Display targetDisplay) {
		d = targetDisplay;
	}

	private void doUpdate() {
		for (;;) {
			Runnable next;
			synchronized (pendingWork) {
				if (pendingWork.isEmpty()) {
					break;
				}
