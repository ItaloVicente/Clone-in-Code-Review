	private void compactPacks(DfsReader ctx
			throws IOException
		DfsObjDatabase objdb = repo.getObjectDatabase();
		PackConfig pc = new PackConfig(repo);
		pc.setIndexVersion(2);
		pc.setDeltaCompress(false);
		pc.setReuseDeltas(true);
		pc.setReuseObjects(true);

		PackWriter pw = new PackWriter(pc
		try {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);

			addObjectsToPack(pw
			if (pw.getObjectCount() == 0) {
				return;
			}

			boolean rollback = true;
			initOutDesc(objdb);
