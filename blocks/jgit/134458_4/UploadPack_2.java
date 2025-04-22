		if (refs != null) {
			return refs;
		}

		if (!advertiseRefsHookCalled) {
			advertiseRefsHook.advertiseRefs(this);
			advertiseRefsHookCalled = true;
		}
		if (refs == null) {
			setAdvertisedRefs(
					db.getRefDatabase().getRefs().stream()
						.collect(toMap(Ref::getName
		}
