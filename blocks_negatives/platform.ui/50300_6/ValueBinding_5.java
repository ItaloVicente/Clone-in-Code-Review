class ValueBinding extends Binding {
	private final UpdateValueStrategy targetToModel;
	private final UpdateValueStrategy modelToTarget;
	private WritableValue validationStatusObservable;
	private IObservableValue target;
	private IObservableValue model;
