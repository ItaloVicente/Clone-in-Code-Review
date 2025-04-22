	private IListChangeListener targetChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			if (!updatingTarget) {
				doUpdate((IObservableList) getTarget(),
						(IObservableList) getModel(), event.diff,
						targetToModel, false, false);
			}
