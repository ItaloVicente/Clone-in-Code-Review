
	@Test
	public void testMergeInto() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION

		RevCommit c1 = commit();
		Ref branch1 = branch(c1
		RevCommit c2 = commit(c1);
		Ref branch2 = branch(c2
		RevCommit c3 = commit(c2);
		Ref branch3 = branch(c3
		RevCommit c4 = commit(c1);
		Ref branch4 = branch(c4
		RevCommit c5 = commit(c4);
		Ref branch5 = branch(c5
		RevCommit c6 = commit(c1);
		Ref branch6 = branch(c6
		RevCommit c7 = commit(c2
		Ref branch7 = branch(c7
		RevCommit c8 = commit(c5);
		Ref branch8 = branch(c8
		RevCommit c9 = commit(c4
		Ref branch9 = branch(c9
		writeCommitGraph();


		assertRefsEquals(asList(branch8)
		assertRefsEquals(asList(branch5
		assertRefsEquals(asList(branch4
				allMergedInto(c4));
		assertRefsEquals(asList(branch2
		assertRefsEquals(asList(branch1
				branch6
	}

	List<Ref> allMergedInto(RevCommit needle) throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefs();
		return rw.getMergedInto(needle
	}

	void assertRefsEquals(List<Ref> expecteds
		assertArrayEquals(expecteds.toArray()
	}

