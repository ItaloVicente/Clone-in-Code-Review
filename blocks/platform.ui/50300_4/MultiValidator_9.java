	private IObservableValue<IStatus> unmodifiableValidationStatus;
	private WritableList<IObservable> targets;
	private IObservableList<IObservable> unmodifiableTargets;
	private IObservableList<IObservable> models;

	IListChangeListener<IObservable> targetsListener = new IListChangeListener<IObservable>() {
		public void handleListChange(ListChangeEvent<IObservable> event) {
			event.diff.accept(new ListDiffVisitor<IObservable>() {
