	@Test
	public void testShallowIsMinimal() throws Exception {
		FileRepository repo = createBareRepository();
		TestRepository<Repository> r = new TestRepository<Repository>(repo);
		BranchBuilder bb = r.branch("refs/heads/master");
		RevBlob contentA = r.blob("A");
		RevBlob contentB = r.blob("B");
		RevBlob contentC = r.blob("C");
		RevBlob contentD = r.blob("D");
		RevBlob contentE = r.blob("E");
		RevCommit c1 = bb.commit().add("a"
		RevCommit c2 = bb.commit().add("b"
		RevCommit c3 = bb.commit().add("c"
		RevCommit c4 = bb.commit().add("d"
		RevCommit c5 = bb.commit().add("e"
		r.getRevWalk().parseHeaders(c1);
		r.getRevWalk().parseHeaders(c2);
		r.getRevWalk().parseHeaders(c3);
		r.getRevWalk().parseHeaders(c4);
		r.getRevWalk().parseHeaders(c5);

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c1.getId()
						c2.getTree().getId()
						contentB.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentD.getId()
	}

