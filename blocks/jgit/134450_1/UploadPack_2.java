		if (refs != null) {
			return refs;
		}

		advertiseRefsHook.advertiseRefs(this);
		if (refs == null) {
			setAdvertisedRefs(db.getRefDatabase().getRefs(ALL));
		}
