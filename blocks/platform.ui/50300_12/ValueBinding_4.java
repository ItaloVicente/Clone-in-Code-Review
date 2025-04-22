class ValueBinding<M, T> extends Binding {
	private final UpdateValueStrategy<T, M> targetToModel;
	private final UpdateValueStrategy<M, T> modelToTarget;
	private WritableValue<IStatus> validationStatusObservable;
	private IObservableValue<T> target;
	private IObservableValue<M> model;
