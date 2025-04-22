
	protected int countCommits(Repository repository) throws GitAPIException,
			NoHeadException {
		int count = 0;
		Iterable<RevCommit> commits = Git.wrap(repository).log().call();
		Iterator<RevCommit> iterator = commits.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}
