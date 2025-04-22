		if (i == 0)
			return PostUploadHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PostUploadHookChain(newHooks, i);
