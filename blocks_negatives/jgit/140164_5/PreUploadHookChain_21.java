		if (i == 0)
			return PreUploadHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PreUploadHookChain(newHooks, i);
