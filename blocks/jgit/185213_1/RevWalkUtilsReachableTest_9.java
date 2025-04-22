	@Test
	public void findBranchesReachableManyTimes() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit();
		RevCommit c = commit(a);
		RevCommit d = commit(b);
		Ref branchA = branch("a"
		Ref branchB = branch("b"
		Ref branchC = branch("c"
		Ref branchD = branch("d"

		assertContains(a
		assertContains(b
		assertContains(c
		assertContains(d
	}

