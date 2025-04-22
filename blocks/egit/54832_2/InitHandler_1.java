		List<Ref> branchList;
		try {
			branchList = Git.wrap(gfRepo.getRepository()).branchList().call();
		} catch (GitAPIException e) {
			throw new ExecutionException(e.getMessage());
		}

