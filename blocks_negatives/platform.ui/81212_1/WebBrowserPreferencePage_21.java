		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent e) {
				checkNewDefaultBrowser(e.getElement());
				checkedBrowser = (IBrowserDescriptor) e.getElement();

				Object[] obj = tableViewer.getCheckedElements();
				if (obj.length == 0)
					tableViewer.setChecked(e.getElement(), true);
			}
