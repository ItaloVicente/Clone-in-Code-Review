	private IListChangeListener<M> masterListListener = new IListChangeListener<M>() {
		@Override
		public void handleListChange(ListChangeEvent<? extends M> event) {
			handleMasterListChange(event.diff);
		}
	};
