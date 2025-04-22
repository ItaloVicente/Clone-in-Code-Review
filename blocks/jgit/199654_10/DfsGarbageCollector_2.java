
	private void writeCommitGraph(DfsPackDescription pack
			throws IOException {
		if (!writeCommitGraph || !objdb.getShallowCommits().isEmpty()) {
			return;
		}

		Set<ObjectId> allTips = refsBefore.stream().map(Ref::getObjectId)
				.collect(Collectors.toUnmodifiableSet());

		try (DfsOutputStream out = objdb.writeFile(pack
				RevWalk pool = new RevWalk(ctx)) {
			GraphCommits gcs = GraphCommits.fromWalk(pm
			CountingOutputStream cnt = new CountingOutputStream(out);
			CommitGraphWriter writer = new CommitGraphWriter(gcs);
			writer.write(pm
			pack.addFileExt(COMMIT_GRAPH);
			pack.setFileSize(COMMIT_GRAPH
			pack.setBlockSize(COMMIT_GRAPH
		}
	}
