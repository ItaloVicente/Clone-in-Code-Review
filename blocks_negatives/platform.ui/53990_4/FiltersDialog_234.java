    private ICheckStateListener checkStateListener = new ICheckStateListener() {
        @Override
		public void checkStateChanged(CheckStateChangedEvent event) {
            FiltersDialog.this.checkStateChanged(event);
        }
    };
