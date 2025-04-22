	public static PostUploadHook newChain(List<? extends PostUploadHook> hooks) {
		PostUploadHook[] newHooks = new PostUploadHook[hooks.size()];
		int i = 0;
		for (PostUploadHook hook : hooks)
			if (hook != PostUploadHook.NULL)
				newHooks[i++] = hook;
		if (i == 0)
