        fViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                updateOKStatus();
            }
        });
