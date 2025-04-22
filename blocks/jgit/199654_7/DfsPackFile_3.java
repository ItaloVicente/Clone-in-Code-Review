	public CommitGraph getCommitGraph(DfsReader ctx) throws IOException {
		if (invalid || isGarbage() || !desc.hasFileExt(COMMIT_GRAPH)) {
			return null;
		}

		if (commitGraph != null) {
			return commitGraph;
		}

		DfsStreamKey commitGraphKey = desc.getStreamKey(COMMIT_GRAPH);
		AtomicBoolean cacheHit = new AtomicBoolean(true);
		DfsBlockCache.Ref<CommitGraph> cgref = cache
				.getOrLoadRef(commitGraphKey
					cacheHit.set(false);
					return loadCommitGraph(ctx
				});
		if (cacheHit.get()) {
			ctx.stats.commitGraphCacheHit++;
		}
		CommitGraph cg = cgref.get();
		if (commitGraph == null && cg != null) {
			commitGraph = cg;
		}
		return commitGraph;
	}

