	private IObservableValue unmodifiableValidationStatus;
	private WritableList targets;
	private IObservableList unmodifiableTargets;
	private IObservableList models;

	IListChangeListener targetsListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
