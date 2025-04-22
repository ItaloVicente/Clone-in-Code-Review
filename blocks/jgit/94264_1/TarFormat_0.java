		
		if (tree instanceof RevCommit) {
			long t = ((RevCommit) tree).getCommitTime() * 1000L;
			entry.setModTime(t);
		}
		
