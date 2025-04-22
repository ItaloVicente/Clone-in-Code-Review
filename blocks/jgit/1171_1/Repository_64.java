public abstract class Repository {
	private static final ListenerList globalListeners = new ListenerList();

	public static ListenerList getGlobalListenerList() {
		return globalListeners;
	}

