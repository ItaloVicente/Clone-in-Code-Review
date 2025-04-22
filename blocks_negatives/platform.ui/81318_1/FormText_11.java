		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				model.dispose();
				disposeResourceTable(true);
			}
