	private IListChangeListener<IObservable> targetsChangeListener = new IListChangeListener<IObservable>() {

		@Override
		public void handleListChange(ListChangeEvent<? extends IObservable> event) {
			event.diff.accept(new TargetsListDiffAdvisor<>());
			statusChanged(validationStatus.getValue());
		}

