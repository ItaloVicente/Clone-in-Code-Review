	public interface RefsUpdateHook {
		void preExecute(BatchRefUpdate bru);
	}

	private RefsUpdateHook refsUpdateHook;

