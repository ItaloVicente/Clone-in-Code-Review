public class SetDelegatingValueObservableMap<S, K extends S, V> extends
		AbstractObservableMap<K, V> implements
		IPropertyObservable<DelegatingValueProperty<S, V>> {
	private IObservableSet<K> masterSet;
	private DelegatingValueProperty<S, V> detailProperty;
	private DelegatingCache<S, K, V> cache;
