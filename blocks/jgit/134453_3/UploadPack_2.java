		if (refs != null) {
			return refs;
		}

		advertiseRefsHook.advertiseRefs(this);
		if (refs == null) {
