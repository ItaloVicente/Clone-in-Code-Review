	private IListChangeListener modelChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			if (!updatingModel) {
				doUpdate((IObservableList) getModel(),
						(IObservableList) getTarget(), event.diff,
						modelToTarget, false, false);
			}
