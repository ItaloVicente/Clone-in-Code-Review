	@Test
	public void reachable_self() throws Exception {
		RevCommit a = repo.commit().create();
		String name = a.name();

		RevCommit different = repo.getRepository().parseCommit(ObjectId.fromString(name));
		ReachabilityChecker checker = getChecker(repo);
		assertReachable("reachable from itself"
				checker.areAllReachable(Arrays.asList(a)
	}

