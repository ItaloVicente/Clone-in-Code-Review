		cleanTable.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				updatePageComplete();
			}
		});
