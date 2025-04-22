        projectNames.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                selection = projectNames.getCheckedElements();
                updateEnablement();
            }
        });
