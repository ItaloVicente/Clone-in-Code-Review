		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent e) {
				checkNewDefaultBrowser(e.getElement());
				checkedBrowser = (IBrowserDescriptor) e
						.getElement();
