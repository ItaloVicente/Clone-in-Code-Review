	@Test
	public void testLooseObjectStaleFileHandle() throws Exception {
		WindowCursor repo1Cur = new WindowCursor(db.getObjectDatabase());

		GC externalGCOnRepo = new GC(db);

		externalGCOnRepo.setExpireAgeMillis(1000000);
		externalGCOnRepo.setPackExpireAgeMillis(1000000);

		ObjectId id = commitFile("file.txt"
		for (int i = 0; i < 100; i++) {
			externalGCOnRepo.gc();
			commitFile("file" + i +".txt"
		}

		for (int i = 0; i < 100; i++) {
			commitFile("another-file" + i + ".txt"
		}

		System.out.println("Start GC");
		long start_time = System.currentTimeMillis();
		externalGCOnRepo.gc();
		long end_time = System.currentTimeMillis();
		double difference = (end_time - start_time);
		System.out.println("End GC in " + difference);
	}

