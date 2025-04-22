		LogCommand cmd = new Git(repo).log().addRange(upstreamCommit,
				headCommit);
		Iterable<RevCommit> commitsToUse = cmd.call();

		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();
		for (RevCommit commit : commitsToUse) {
			if (commit.getParentCount() != 1)
				continue;
			cherryPickList.add(commit);
		}

		Collections.reverse(cherryPickList);
