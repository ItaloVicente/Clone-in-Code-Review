        tree.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                handleCheckStateChange(event);
            }
        });
