		setPageComplete(false);
		cleanTable.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				updatePageComplete();
			}
		});
