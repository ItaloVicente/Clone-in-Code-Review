        listViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                modified = true;
            }
        });
