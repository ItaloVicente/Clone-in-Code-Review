public class ListenerHandle {
	private final ListenerList parent;

	final Class<? extends RepositoryListener> type;

	final RepositoryListener listener;

	ListenerHandle(ListenerList parent
			Class<? extends RepositoryListener> type
			RepositoryListener listener) {
		this.parent = parent;
		this.type = type;
		this.listener = listener;
	}

	public void remove() {
		parent.remove(this);
