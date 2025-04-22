		if (i == 0)
			return PreReceiveHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PreReceiveHookChain(newHooks, i);
