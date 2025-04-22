	private void addResourceDisposeListener(Control control, final Resource resource) {
		control.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				resource.dispose();
			}
		});
	}

