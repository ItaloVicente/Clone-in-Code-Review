		bar.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				cleanUp((MToolBar) element);
				toolbarMenu = null;
			}
		});
