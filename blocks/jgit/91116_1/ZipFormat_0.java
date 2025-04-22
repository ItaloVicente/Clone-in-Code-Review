
		if(tree instanceof RevCommit){
			long commitTime = ((RevCommit) tree).getCommitTime();
			entry.setTime(commitTime);
		}

