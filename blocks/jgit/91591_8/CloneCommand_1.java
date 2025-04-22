	private Callback callback;

	public interface Callback {
		void initializedSubmodules(Collection<String> submodules);

		void cloningSubmodule(String path);

		void checkingOut(AnyObjectId commit
	}

