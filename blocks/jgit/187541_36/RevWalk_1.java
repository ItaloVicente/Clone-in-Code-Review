
			int cutoff = Integer.MAX_VALUE;
			if (needle instanceof RevCommitCG) {
				needle.parseHeaders(this);
				cutoff = needle.getGeneration();
			}
