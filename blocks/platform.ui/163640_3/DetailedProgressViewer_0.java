	private ListenerList<IProgressViewerListener> listeners = new ListenerList<>();

	interface IProgressViewerListener {
		void handle(int elementsCount);
	}

