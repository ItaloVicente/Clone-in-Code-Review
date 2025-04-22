        ICheckStateListener listener = new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                updateWidgetEnablements();
            }
        };
