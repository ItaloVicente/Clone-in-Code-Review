	private Listener previousFocusListener = e -> {
		if (e.widget instanceof Control && e.widget != quickAccessButton) {
			previousFocusControl = (Control) e.widget;
		}
	};
	private Control previousFocusControl;
