		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				traversalFilterManager.dispose();
			}
		});
