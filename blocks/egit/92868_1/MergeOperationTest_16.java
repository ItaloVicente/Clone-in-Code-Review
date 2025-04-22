		try (Git git = new Git(testRepository.getRepository())) {
			LogCommand log = git.log();
			Iterable<RevCommit> commits = log.call();
			int result = 0;
			for (Iterator i = commits.iterator(); i.hasNext();) {
				i.next();
				result++;
			}
			return result;
