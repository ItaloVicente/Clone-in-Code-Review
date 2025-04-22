		projectsList.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				enableSelectAllButtons();
			}
		});

		final Object[] children = new Object[0];
