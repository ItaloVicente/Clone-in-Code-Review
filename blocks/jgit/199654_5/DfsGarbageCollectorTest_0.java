	@Test
	public void produceCommitGraphAllRefsIncluded() throws Exception {
		String tag = "refs/tags/tag1";
		String head = "refs/heads/head1";
		String nonHead = "refs/something/nonHead";

		RevCommit rootCommitTagged = git.branch(tag).commit().message("0")
				.noParents().create();
		RevCommit headTip = git.branch(head).commit().message("1")
				.parent(rootCommitTagged).create();
		RevCommit nonHeadTip = git.branch(nonHead).commit().message("2")
				.parent(rootCommitTagged).create();

		gcWithCommitGraph();

		assertEquals(2
		DfsPackFile gcPack = odb.getPacks()[0];
		assertEquals(GC

		DfsReader reader = odb.newReader();
		CommitGraph cg = gcPack.getCommitGraph(reader);
		assertNotNull(cg);
		CommitGraph cachedCG = gcPack.getCommitGraph(reader);
		assertNotNull(cachedCG);
		assertTrue("commit graph have been read from disk once"
				reader.stats.readCommitGraph == 1);
		assertTrue("commit graph read contains content"
				reader.stats.readCommitGraphBytes > 0);
		assertTrue("commit graph read time is recorded"
				reader.stats.readCommitGraphMicros > 0);

		assertTrue("all commits in commit graph"
		assertTrue("tag referenced commit is in graph"
				cg.findGraphPosition(rootCommitTagged) != -1);
		assertTrue("head referenced commit is in graph"
				cg.findGraphPosition(headTip) != -1);
		assertTrue("nonHead referenced commit is in graph"
				cg.findGraphPosition(nonHeadTip) != -1);
	}

	@Test
	public void noCommitGraphWithoutGcPack() throws Exception {
		String nonHead = "refs/something/nonHead";
		RevCommit nonHeadCommit = git.branch(nonHead).commit()
				.message("nonhead").noParents().create();
		commit().message("unreachable").parent(nonHeadCommit).create();

		gcWithCommitGraph();

		for (DfsPackFile pack : odb.getPacks()) {
			assertNull(pack.getCommitGraph(odb.newReader()));
		}
	}

	@Test
	public void commitGraphWithoutGCrestPack() throws Exception {
		String head = "refs/heads/head1";
		RevCommit headCommit = git.branch(head).commit().message("head")
				.noParents().create();
		RevCommit unreachableCommit = commit().message("unreachable")
				.parent(headCommit).create();

		gcWithCommitGraph();

		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				CommitGraph cg = pack.getCommitGraph(odb.newReader());
				assertNotNull(cg);
				assertTrue("commit graph only contains 1 commit"
						cg.getCommitCnt() == 1);
				assertTrue("head exists in commit graph"
						cg.findGraphPosition(headCommit) != -1);
				assertTrue("unreachable commit does not exist in commit graph"
						cg.findGraphPosition(unreachableCommit) == -1);
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				CommitGraph cg = pack.getCommitGraph(odb.newReader());
				assertNull(cg);
			} else {
				fail("unexpected " + d.getPackSource());
				break;
			}
		}
	}

