	public DfsCommitGraph[] getCommitGraphs() throws IOException {
		return getPackList().commitGraphs;
	}

	public Optional<DfsCommitGraph> getGCCommitGraph() throws IOException {
		Optional<DfsCommitGraph> latest = Arrays.stream(getPackList().commitGraphs).min(commitGraphComparator());
		if (latest.isEmpty() || latest.get().getPackDescription().getPackSource() != PackSource.GC){
			return Optional.empty();
		}
		return latest;
	}

