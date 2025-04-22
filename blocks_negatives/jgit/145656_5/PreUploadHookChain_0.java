	public static PreUploadHook newChain(List<? extends PreUploadHook> hooks) {
		PreUploadHook[] newHooks = new PreUploadHook[hooks.size()];
		int i = 0;
		for (PreUploadHook hook : hooks)
			if (hook != PreUploadHook.NULL)
				newHooks[i++] = hook;
		if (i == 0)
