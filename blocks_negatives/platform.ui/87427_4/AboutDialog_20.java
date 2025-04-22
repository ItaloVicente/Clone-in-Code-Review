		text.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				textManager.dispose();
			}
		});
