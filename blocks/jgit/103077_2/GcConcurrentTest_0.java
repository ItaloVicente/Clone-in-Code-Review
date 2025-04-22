
	@Test
	public void repackAndGetStats() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"
		GC gc1 = new GC(tr.getRepository());
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();
		test.commit().add("b"

		FileRepository r2 = new FileRepository(
				tr.getRepository().getDirectory());
		GC gc2 = new GC(r2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();

		new GC(tr.getRepository()).getStatistics();
	}

	@Test
	public void repackAndUploadPack() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"

		GC gc1 = new GC(tr.getRepository());
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();

		RevCommit b = test.commit().add("b"

		FileRepository r2 = new FileRepository(
				tr.getRepository().getDirectory());
		GC gc2 = new GC(r2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();

		try (PackWriter pw = new PackWriter(tr.getRepository())) {
			pw.setUseBitmaps(true);
			pw.preparePack(NullProgressMonitor.INSTANCE
					Collections.<ObjectId> emptySet());
			new GC(tr.getRepository()).getStatistics();
		}
	}

	PackFile getSinglePack(FileRepository r) {
		Collection<PackFile> packs = r.getObjectDatabase().getPacks();
		assertEquals(1
		return packs.iterator().next();
	}

	@Test
	public void repackAndCheckBitmapUsage() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"
		FileRepository repository = tr.getRepository();
		GC gc1 = new GC(repository);
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();
		String oldPackName = getSinglePack(repository).getPackName();
		RevCommit b = test.commit().add("b"

		FileRepository repository2 = new FileRepository(repository.getDirectory());
		GC gc2 = new GC(repository2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();
		String newPackName = getSinglePack(repository2).getPackName();
		assertNotEquals(oldPackName

		assertNotEquals(getSinglePack(repository).getPackName()

		repository.getObjectDatabase().open(b).getSize();
		assertEquals(getSinglePack(repository).getPackName()
		assertNotNull(getSinglePack(repository).getBitmapIndex());
	}
