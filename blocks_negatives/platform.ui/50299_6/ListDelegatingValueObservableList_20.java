public class ListDelegatingValueObservableList extends AbstractObservableList
		implements IPropertyObservable {
	private IObservableList masterList;
	private DelegatingValueProperty detailProperty;
	private DelegatingCache cache;

	private IListChangeListener masterListener = new IListChangeListener() {
