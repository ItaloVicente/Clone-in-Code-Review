	private void preparedBloomFilter(TreeFilter tf
		commitGraph = walker.getObjectReader().getCommitGraph();
		if (commitGraph == null) {
			return;
		}
		bloomKeys = BloomKeyParser.parse(tf
	}

	private boolean checkMaybeDifferentInBloomFilter(RevCommit commit) {
		if (commitGraph == null || bloomKeys == null) {
			return true;
		}

		if (commit.generation == CommitGraph.GENERATION_UNKNOWN) {
			return true;
		}

		BloomFilter filter = commitGraph.findBloomFilter(commit);

		if (filter == null) {
			return true;
		}

		for (BloomFilter.Key key : bloomKeys) {
			if (filter.contains(key)) {
				return true;
			}
		}
		return false;
	}

