		Listener listener = createListener();
		int[] eventsToListen = { SWT.Paint, SWT.KeyDown, SWT.MouseDown,
				SWT.MouseDoubleClick };

		for (int event : eventsToListen) {
			addListener(event, listener);
		}
		getParent().addListener(SWT.FocusIn, listener);
	}

	private Listener createListener() {
		return new Listener() {
