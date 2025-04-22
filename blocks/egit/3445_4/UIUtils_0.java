	public static void hookDisposal(Widget widget, final Resource resource) {
		if (widget == null || resource == null)
			return;

		widget.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				resource.dispose();
			}
		});
	}
