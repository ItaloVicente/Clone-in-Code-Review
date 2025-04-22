		if (refs != null) {
			return refs;
		}

		advertiseRefsHook.advertiseRefs(this);
		advertiseRefsHookCalled = true;
		if (refs == null) {
			setAdvertisedRefs(
					db.getRefDatabase().getRefs().stream()
						.collect(toMap(Ref::getName
		}
