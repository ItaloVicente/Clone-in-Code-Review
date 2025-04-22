	private ICheckStateListener checkListener = new ICheckStateListener() {

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			CustomizationTab.this.checkStateChanged(event);
		}

	};
