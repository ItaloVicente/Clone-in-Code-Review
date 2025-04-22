		db = into;
		walk = new RevWalk(db);

		final ReceiveConfig cfg = db.getConfig().get(ReceiveConfig.KEY);
		checkReceivedObjects = cfg.checkReceivedObjects;
		allowCreates = cfg.allowCreates;
		allowDeletes = cfg.allowDeletes;
		allowNonFastForwards = cfg.allowNonFastForwards;
		allowOfsDelta = cfg.allowOfsDelta;
		advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
		refFilter = RefFilter.DEFAULT;
