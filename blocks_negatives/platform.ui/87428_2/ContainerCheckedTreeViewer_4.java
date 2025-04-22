        addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                doCheckStateChanged(event.getElement());
            }
        });
