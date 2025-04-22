		try (TestRepository<FileRepository> testRepo = new TestRepository<>(
				repo)) {
			BranchBuilder bb = testRepo.branch("refs/heads/master");
			contentA = testRepo.blob("A");
			c1 = bb.commit().add("f"
			testRepo.getRevWalk().parseHeaders(c1);
			PackIndex pf1 = writePack(repo
			assertContent(pf1
					contentA.getId()));
			contentB = testRepo.blob("B");
			c2 = bb.commit().add("f"
			testRepo.getRevWalk().parseHeaders(c2);
			PackIndex pf2 = writePack(repo
					Sets.of((ObjectIdSet) pf1));
			assertContent(pf2
					contentB.getId()));
		}
