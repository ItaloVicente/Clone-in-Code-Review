		PackConfig cfg = new PackConfig(packConfig);
		cfg.setReuseDeltas(true);
		cfg.setReuseObjects(true);
		cfg.setDeltaCompress(false);
		cfg.setBuildBitmaps(false);

		PackWriter pw = new PackWriter(cfg
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(true);
