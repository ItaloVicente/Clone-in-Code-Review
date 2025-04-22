		getShell().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (objectReader != null)
					objectReader.release();
			}
		});
