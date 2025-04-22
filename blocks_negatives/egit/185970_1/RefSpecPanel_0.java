		specsGroup.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				imageRegistry.dispose();
				errorBackgroundColor.dispose();
				errorTextColor.dispose();
			}
		});
