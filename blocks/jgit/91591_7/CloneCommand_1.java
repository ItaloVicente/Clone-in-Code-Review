	private Callback callback;

	public interface Callback {
		void initializedSubmodules(Collection<String> submodules);

		void cloningSubmodule(String name);
	}

