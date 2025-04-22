        ICheckStateListener listener = new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                getOkButton().setEnabled(
                        selectionGroup.getCheckedElementCount() > 0);
            }
        };
