	public static void hookDisposal(Widget widget,
			final ResourceManager resources) {
		if (widget == null || resources == null)
			return;

		widget.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				resources.dispose();
			}
		});
	}

