	private IChangeListener markDirtyChangeListener = new IChangeListener() {
		@Override
		public void handleChange(ChangeEvent event) {
			markDirty();
		}
	};
