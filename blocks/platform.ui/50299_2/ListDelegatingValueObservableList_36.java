public class ListDelegatingValueObservableList<S, T extends S, E> extends
		AbstractObservableList<E> implements
		IPropertyObservable<DelegatingValueProperty<S, E>> {
	private IObservableList<T> masterList;
	private DelegatingValueProperty<S, E> detailProperty;
	private DelegatingCache<S, T, E> cache;

	private IListChangeListener<T> masterListener = new IListChangeListener<T>() {
