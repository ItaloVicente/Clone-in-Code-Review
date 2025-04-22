	private Set<IContextListener> listeners;

	private String parentId;

	Context(final String id) {
		super(id);
	}

	public final void addContextListener(final IContextListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		if (listeners == null) {
			listeners = new HashSet<>();
		}

		listeners.add(listener);
	}

	@Override
