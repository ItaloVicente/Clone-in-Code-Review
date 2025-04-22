		PackWriter pw = new PackWriter(cfg, ctx);
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(true);
		try {
			RevWalk pool = new RevWalk(ctx);
			pm.beginTask("Finding garbage", objectsBefore());
