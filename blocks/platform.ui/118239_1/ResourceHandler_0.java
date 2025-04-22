	@Inject
	@Optional
	@Named(IWorkbench.PERSIST_STATE)
	private boolean saveAndRestore;

	@Inject
	@Optional
	@Named(IWorkbench.CLEAR_PERSISTED_STATE)
	private boolean clearPersistedState;

