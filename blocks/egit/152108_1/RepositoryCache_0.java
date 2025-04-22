

	private final ReferenceQueue<RepositoryHandle> queue = new ReferenceQueue<>();

	private final Map<File, RepositoryReference> repositoryCache = new HashMap<>();

	private final ListenerList globalListeners = new ListenerList();

	RepositoryCache() {
		new Closer(queue).start();
		ListenerList global = Repository.getGlobalListenerList();
		global.addConfigChangedListener(event -> {
			Repository repo = getRepository(
					event.getRepository().getDirectory());
			if (repo == null || repo == event.getRepository()) {
				globalListeners.dispatch(event);
			} else {
				ConfigChangedEvent newEvent = new ConfigChangedEvent();
				newEvent.setRepository(repo);
				globalListeners.dispatch(newEvent);
			}
		});
		global.addIndexChangedListener(event -> {
			Repository repo = getRepository(
					event.getRepository().getDirectory());
			if (repo == null || repo == event.getRepository()) {
				globalListeners.dispatch(event);
			} else {
				IndexChangedEvent newEvent = new IndexChangedEvent(
						event.isInternal());
				newEvent.setRepository(repo);
				globalListeners.dispatch(newEvent);
			}
		});
		global.addRefsChangedListener(event -> {
			Repository repo = getRepository(
					event.getRepository().getDirectory());
			if (repo == null || repo == event.getRepository()) {
				globalListeners.dispatch(event);
			} else {
				RefsChangedEvent newEvent = new RefsChangedEvent();
				newEvent.setRepository(repo);
				globalListeners.dispatch(newEvent);
			}
		});
		global.addWorkingTreeModifiedListener(event -> {
			Repository repo = getRepository(
					event.getRepository().getDirectory());
			if (repo == null || repo == event.getRepository()) {
				globalListeners.dispatch(event);
			} else {
				WorkingTreeModifiedEvent newEvent = new WorkingTreeModifiedEvent(
						event.getModified(), event.getDeleted());
				newEvent.setRepository(
						getRepository(event.getRepository().getDirectory()));
				globalListeners.dispatch(newEvent);
			}
		});
	}

	public ListenerList getGlobalListenerList() {
		return globalListeners;
	}
