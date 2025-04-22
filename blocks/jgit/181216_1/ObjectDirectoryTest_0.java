	@Test
	public void testLooseObjectStaleFileHandle() throws Exception {
		FileRepository repoReceivingGC = new FileRepository(db.getDirectory());

		GC externalGC = new GC(repoReceivingGC);
		externalGC.setExpireAgeMillis(0);
		externalGC.setPackExpireAgeMillis(0);

		ObjectId id = commitFile("file.txt"

		externalGC.gc();

		db.getObjectDatabase().openObject(new WindowCursor(db.getObjectDatabase())
	}

