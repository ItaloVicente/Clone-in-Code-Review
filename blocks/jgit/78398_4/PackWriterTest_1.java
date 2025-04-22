	@Test
	public void testShallowFetchShallowParent() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentB.getId()
						contentE.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				c2.getTree().getId()
	}

	@Test
	public void testShallowFetchShallowAncestor() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentB.getId()
						contentE.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				c1.getTree().getId()
	}

	private FileRepository setupRepoForShallowFetch() throws Exception {
		FileRepository repo = createBareRepository();
		TestRepository<Repository> r = new TestRepository<Repository>(repo);
		BranchBuilder bb = r.branch("refs/heads/master");
		contentA = r.blob("A");
		contentB = r.blob("B");
		contentC = r.blob("C");
		contentD = r.blob("D");
		contentE = r.blob("E");
		c1 = bb.commit().add("a"
		c2 = bb.commit().add("b"
		c3 = bb.commit().add("c"
		c4 = bb.commit().add("d"
		c5 = bb.commit().add("e"
		r.getRevWalk().parseHeaders(c1);
		r.getRevWalk().parseHeaders(c2);
		r.getRevWalk().parseHeaders(c3);
		r.getRevWalk().parseHeaders(c4);
		r.getRevWalk().parseHeaders(c5);
		return repo;
	}

