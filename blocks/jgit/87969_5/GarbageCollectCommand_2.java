	public GarbageCollectCommand setPreserveOldPacks(boolean preserveOldPacks) {
		if (pconfig == null)
			pconfig = new PackConfig(repo);

		pconfig.setPreserveOldPacks(preserveOldPacks);
		return this;
	}

	public GarbageCollectCommand setPrunePreserved(boolean prunePreserved) {
		if (pconfig == null)
			pconfig = new PackConfig(repo);

		pconfig.setPrunePreserved(prunePreserved);
		return this;
	}

