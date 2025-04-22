	private int countCommits() throws GitAPIException, NoHeadException,
			IOException {
		Iterable<RevCommit> commits = Git.wrap(repository).log().all().call();
		Iterator<RevCommit> iterator = commits.iterator();
		int count = 0;

		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

