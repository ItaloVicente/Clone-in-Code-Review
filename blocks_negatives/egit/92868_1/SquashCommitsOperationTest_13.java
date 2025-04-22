		LogCommand log = new Git(testRepository.getRepository()).log();
		Iterable<RevCommit> commits = log.call();
		int result = 0;
		for (Iterator i = commits.iterator(); i.hasNext();) {
			i.next();
			result++;
