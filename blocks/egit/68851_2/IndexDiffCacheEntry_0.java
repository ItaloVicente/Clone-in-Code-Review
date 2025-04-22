	private final IndexChangedListener indexChangedListener = new IndexChangedListener() {
		@Override
		public void onIndexChanged(IndexChangedEvent event) {
			refreshIndexDelta();
		}
	};

	private final RefsChangedListener refsChangedListener = new RefsChangedListener() {
		@Override
		public void onRefsChanged(RefsChangedEvent event) {
			scheduleReloadJob("RefsChanged"); //$NON-NLS-1$
		}
	};

	private final Set<ListenerHandle> indexChangedListenerHandles = new HashSet<>();

	private final Set<ListenerHandle> refsChangedListenerHandles = new HashSet<>();

	private final Set<Repository> submodules = new HashSet<>();
