class ValueBinding<M, T> extends Binding {
	private final UpdateValueStrategy<? super T, ? extends M> targetToModel;
	private final UpdateValueStrategy<? super M, ? extends T> modelToTarget;
	private WritableValue<IStatus> validationStatusObservable;
	private IObservableValue<T> target;
	private IObservableValue<M> model;
