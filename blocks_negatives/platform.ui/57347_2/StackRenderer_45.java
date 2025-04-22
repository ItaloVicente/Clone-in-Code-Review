		ctrl.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!swtMenu.isDisposed()) {
					swtMenu.dispose();
				}
