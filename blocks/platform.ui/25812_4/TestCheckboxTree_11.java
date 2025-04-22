		viewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent e) {
				checkChildren((TestElement) e.getElement(), e.getChecked());
			}
		});
