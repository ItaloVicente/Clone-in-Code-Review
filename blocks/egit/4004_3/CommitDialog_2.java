		final Menu menu = new Menu(filesViewer.getTable());
		filesViewer.getTable().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				menu.dispose();
			}
		});
