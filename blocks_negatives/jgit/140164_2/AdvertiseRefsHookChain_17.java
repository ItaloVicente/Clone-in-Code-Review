		if (i == 0)
			return AdvertiseRefsHook.DEFAULT;
		else if (i == 1)
			return newHooks[0];
		else
			return new AdvertiseRefsHookChain(newHooks, i);
