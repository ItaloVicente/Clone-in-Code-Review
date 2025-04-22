		parent.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (errorOnWidgetDisposal) {
					throw new RuntimeException();
				}
