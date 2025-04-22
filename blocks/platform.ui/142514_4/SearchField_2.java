	private Listener previousFocusListener = e -> {
		if (e.widget instanceof Control && e.widget != txtQuickAccess) {
			previousFocusControl = (Control) e.widget;
		}
	};
	private Control previousFocusControl;
