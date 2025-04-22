	@Test
	public void testExclude() throws Exception {
		FileRepository repo = createBareRepository();

		TestRepository<FileRepository> testRepo = new TestRepository<FileRepository>(
				repo);
		BranchBuilder bb = testRepo.branch("refs/heads/master");
		RevBlob contentA = testRepo.blob("A");
		RevCommit c1 = bb.commit().add("f"
		testRepo.getRevWalk().parseHeaders(c1);
		PackIndex pf1 = writePack(repo
				Collections.<PackIndex> emptySet());
		assertContent(
				pf1
				Arrays.asList(c1.getId()
						contentA.getId()));
		RevBlob contentB = testRepo.blob("B");
		RevCommit c2 = bb.commit().add("f"
		testRepo.getRevWalk().parseHeaders(c2);
		PackIndex pf2 = writePack(repo
				Collections.singleton(pf1));
		assertContent(
				pf2
				Arrays.asList(c2.getId()
						contentB.getId()));
	}

	private void assertContent(PackIndex pi
		assertEquals("Pack index has wrong size."
				pi.getObjectCount());
		for (int i = 0; i < pi.getObjectCount(); i++)
			assertTrue(
					"Pack index didn't contain the expected id "
							+ pi.getObjectId(i)
					expected.contains(pi.getObjectId(i)));
	}

	private PackIndex writePack(FileRepository repo
			Set<? extends ObjectId> want
			throws IOException {
		PackWriter pw = new PackWriter(repo);
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		for (PackIndex idx : excludeObjects)
			pw.excludeObjects(idx);
		pw.preparePack(NullProgressMonitor.INSTANCE
				Collections.<ObjectId> emptySet());
		String id = pw.computeName().getName();
		File packdir = new File(repo.getObjectsDirectory()
		File packFile = new File(packdir
		FileOutputStream packOS = new FileOutputStream(packFile);
		pw.writePack(NullProgressMonitor.INSTANCE
				NullProgressMonitor.INSTANCE
		packOS.close();
		File idxFile = new File(packdir
		FileOutputStream idxOS = new FileOutputStream(idxFile);
		pw.writeIndex(idxOS);
		idxOS.close();
		pw.release();
		return PackIndex.open(idxFile);
	}

