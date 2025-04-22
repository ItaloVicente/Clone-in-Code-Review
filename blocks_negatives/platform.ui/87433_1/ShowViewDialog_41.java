		treeControl.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dimmedForeground.dispose();
			}
		});
