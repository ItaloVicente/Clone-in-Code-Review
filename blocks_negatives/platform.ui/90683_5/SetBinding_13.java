	private ISetChangeListener modelChangeListener = new ISetChangeListener() {
		@Override
		public void handleSetChange(SetChangeEvent event) {
			if (!updatingModel) {
				doUpdate((IObservableSet) getModel(),
						(IObservableSet) getTarget(), event.diff,
						modelToTarget, false, false);
			}
