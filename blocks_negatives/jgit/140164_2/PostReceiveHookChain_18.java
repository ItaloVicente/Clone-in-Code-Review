		if (i == 0)
			return PostReceiveHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PostReceiveHookChain(newHooks, i);
