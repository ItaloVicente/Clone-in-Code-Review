	private ISetChangeListener targetChangeListener = new ISetChangeListener() {
		@Override
		public void handleSetChange(SetChangeEvent event) {
			if (!updatingTarget) {
				doUpdate((IObservableSet) getTarget(),
						(IObservableSet) getModel(), event.diff, targetToModel,
						false, false);
			}
