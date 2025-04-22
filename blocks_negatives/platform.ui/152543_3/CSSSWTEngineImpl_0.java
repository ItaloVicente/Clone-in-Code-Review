		disposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				handleWidgetDisposed(e.widget);
			}
		};
