public class MapDelegatingValueObservableMap<S, K, I extends S, V> extends
		AbstractObservableMap<K, V> implements
		IPropertyObservable<DelegatingValueProperty<S, V>> {
	private IObservableMap<K, I> masterMap;
	private DelegatingValueProperty<S, V> detailProperty;
	private DelegatingCache<S, I, V> cache;
