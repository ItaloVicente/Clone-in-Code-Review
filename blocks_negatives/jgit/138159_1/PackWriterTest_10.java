		TestRepository<FileRepository> testRepo = new TestRepository<>(
				repo);
		BranchBuilder bb = testRepo.branch("refs/heads/master");
		contentA = testRepo.blob("A");
		c1 = bb.commit().add("f", contentA).create();
		testRepo.getRevWalk().parseHeaders(c1);
		PackIndex pf1 = writePack(repo, wants(c1), EMPTY_ID_SET);
		assertContent(
				pf1,
				Arrays.asList(c1.getId(), c1.getTree().getId(),
						contentA.getId()));
		contentB = testRepo.blob("B");
		c2 = bb.commit().add("f", contentB).create();
		testRepo.getRevWalk().parseHeaders(c2);
		PackIndex pf2 = writePack(repo, wants(c2), Sets.of((ObjectIdSet) pf1));
		assertContent(
				pf2,
				Arrays.asList(c2.getId(), c2.getTree().getId(),
						contentB.getId()));
