	@Test
	public void testMergeInto() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION

		RevCommit c1 = commit();
		Ref branch1 = branch("commits/1"
		RevCommit c2 = commit(c1);
		Ref branch2 = branch("commits/2"
		RevCommit c3 = commit(c2);
		Ref branch3 = branch("commits/3"
		RevCommit c4 = commit(c1);
		Ref branch4 = branch("commits/4"
		RevCommit c5 = commit(c4);
		Ref branch5 = branch("commits/5"
		RevCommit c6 = commit(c1);
		Ref branch6 = branch("commits/6"
		RevCommit c7 = commit(c2
		Ref branch7 = branch("commits/7"
		RevCommit c8 = commit(c5);
		Ref branch8 = branch("commits/8"
		RevCommit c9 = commit(c4
		Ref branch9 = branch("commits/9"
		writeCommitGraph();


		assertRefsEquals(asList(branch8)
		assertRefsEquals(asList(branch5
		assertRefsEquals(asList(branch4
				allMergedInto(c4));
		assertRefsEquals(asList(branch2
		assertRefsEquals(asList(branch1
				branch6
	}

	private List<Ref> allMergedInto(RevCommit needle) throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefs();
		return rw.getMergedInto(needle
	}

	private void assertRefsEquals(List<Ref> expecteds

		assertArrayEquals(expecteds.toArray()
	}

	private Ref branch(String name
		return Git.wrap(db).branchCreate().setName(name)
				.setStartPoint(dst.name()).call();
	}

