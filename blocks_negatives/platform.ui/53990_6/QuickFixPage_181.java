		markersTable.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked() == true) {
					setPageComplete(true);
				} else {
					setPageComplete(markersTable.getCheckedElements().length > 0);
				}

