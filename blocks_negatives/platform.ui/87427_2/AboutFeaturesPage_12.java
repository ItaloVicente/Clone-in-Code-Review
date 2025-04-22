		parent.getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				disposeImages();
			}
		});
