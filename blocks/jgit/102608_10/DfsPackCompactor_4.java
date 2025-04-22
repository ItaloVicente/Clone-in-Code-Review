	private void compactReftables(DfsReader ctx) throws IOException {
		DfsObjDatabase objdb = repo.getObjectDatabase();
		Collections.sort(srcReftables

		try (ReftableStack stack = ReftableStack.open(ctx
			initOutDesc(objdb);
			ReftableCompactor compact = new ReftableCompactor();
			compact.addAll(stack.readers());
			compact.setIncludeDeletes(true);
			writeReftable(objdb
		}
	}

	private void initOutDesc(DfsObjDatabase objdb) throws IOException {
		if (outDesc == null) {
			outDesc = objdb.newPack(COMPACT
		}
	}

