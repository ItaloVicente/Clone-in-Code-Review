		assertOneResult("master~2"
	}

	@Test
	public void onePathMergeSecondParent() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c0).create();
		RevCommit c3 = tr.commit().parent(c2).create();
		RevCommit c4 = tr.commit().parent(c1).parent(c3).create();
		tr.update("master"
		assertOneResult("master^2"
		assertOneResult("master^2~1"
