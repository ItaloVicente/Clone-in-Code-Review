		stage(fileName, localBytes);
		RevCommit firstCommit = commit();
		localBytes[8120] = 'b';
		ObjectId objectId = stage(fileName, localBytes);
		RevCommit secondCommit = commit();
		RevCommitList<RevCommit> baseCommits = new RevCommitList<RevCommit>();
		baseCommits.add(firstCommit);
		baseCommits.add(secondCommit);
