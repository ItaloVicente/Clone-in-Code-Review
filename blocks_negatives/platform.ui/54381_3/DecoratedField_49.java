		control.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent event) {
				if (hover != null) {
					hover.dispose();
				}
