
	private void writeCommitGraph(DfsPackDescription pack
			throws IOException {
		if (writeCommitGraph) {
			Set<ObjectId> allTips = refsBefore.stream().map(Ref::getObjectId)
					.collect(Collectors.toUnmodifiableSet());
			RevWalk pool = new RevWalk(ctx);
			GraphCommits gcs = GraphCommits.fromWalk(pm
			writeCommitGraph(pack
		}
	}

	private void writeCommitGraph(DfsPackDescription pack
			GraphCommits graphCommits
		try (DfsOutputStream out = objdb.writeFile(pack
			CountingOutputStream cnt = new CountingOutputStream(out);
			CommitGraphWriter writer = new CommitGraphWriter(graphCommits);
			writer.write(pm
			pack.addFileExt(COMMIT_GRAPH);
			pack.setFileSize(COMMIT_GRAPH
			pack.setBlockSize(COMMIT_GRAPH
		}
	}
