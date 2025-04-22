		try (Git git = new Git(testRepository.getRepository())) {
			LogCommand log = git.log();
			Iterable<RevCommit> logCommits = log.call();
			RevCommit latestCommit = logCommits.iterator().next();
			assertEquals("squashed", latestCommit.getFullMessage());
		}
