	@Test
	public void gcWriteCommitGraphWithHeadTagNonHead() throws Exception {
		String tag = "refs/tags/tag1";
		String head = "refs/heads/head1";
		String nonHead = "refs/something/nonHead";

		RevCommit commit0 = git.branch(tag).commit().message("0").noParents()
				.create();
		RevCommit commit1 = git.branch(head).commit().message("1")
				.parent(commit0).create();
		RevCommit commit2 = git.branch(nonHead).commit().message("2")
				.parent(commit0).create();

		gcWithCommitGraph();

		assertEquals(2
		DfsPackFile gcPack = odb.getPacks()[0];
		DfsPackDescription desc = gcPack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"
		assertTrue("commit2 in pack"

		DfsReader reader = odb.newReader();
		CommitGraph cg = gcPack.getCommitGraph(reader);
		assertNotNull(cg);
		gcPack.wipeInMemoryCommitGraph();
		CommitGraph cachedCG = gcPack.getCommitGraph(reader);
		assertNotNull(cachedCG);
		assertTrue("commit graph is retrieved from cache once"
				reader.stats.commitGraphCacheHit == 1);
		assertTrue("commit graph have been read from disk once"
				reader.stats.readCommitGraph == 1);
		assertTrue("commit graph read contains content"
				reader.stats.readCommitGraphBytes > 0);
		assertTrue("commit graph read time is recorded"
				reader.stats.readCommitGraphMicros > 0);

		assertTrue("all commits in commit graph"
		assertTrue("tag referenced commit is in graph"
				cg.findGraphPosition(commit0) != -1);
		assertTrue("head referenced commit is in graph"
				cg.findGraphPosition(commit1) != -1);
		assertTrue("nonHead referenced commit is in graph"
				cg.findGraphPosition(commit2) != -1);
	}

	@Test
	public void noCommitGraphGeneratedWithGCRestAndUG() throws Exception {
		String nonHead = "refs/something/nonHead";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(nonHead

		gcWithCommitGraph();

		assertEquals(2
		boolean gcRestPackFound = false;
		boolean garbagePackFound = false;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			switch (d.getPackSource()) {
			case GC_REST:
				gcRestPackFound = true;
				assertTrue("has commit0"
				assertFalse("no commit1"
				assertNull(pack.getCommitGraph(odb.newReader()));
				break;
			case UNREACHABLE_GARBAGE:
				garbagePackFound = true;
				assertFalse("no commit0"
				assertTrue("has commit1"
				assertNull(pack.getCommitGraph(odb.newReader()));
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
			}
		}

		assertTrue("gc pack found"
		assertTrue("garbage pack found"
	}

