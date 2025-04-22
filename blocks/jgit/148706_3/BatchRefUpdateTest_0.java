	@Test
	public void stressTest() throws Exception {
		for (int i = 0; i < 200; i++) {
			BatchRefUpdate bu = diskRepo.getRefDatabase().newBatchUpdate();
			String b1  = String.format("refs/heads/a%d"
			String b2  = String.format("refs/heads/b%d"
			bu.setAtomic(atomic);

			ReceiveCommand c1 = new ReceiveCommand(ObjectId.zeroId()
			ReceiveCommand c2 = new ReceiveCommand(ObjectId.zeroId()

			bu.addCommand(c1
			try (RevWalk rw = new RevWalk(diskRepo)) {
				bu.execute(rw
			}

			assertEquals(c1.getResult()
			assertEquals(c2.getResult()

			assertNotNull(String.format("run %d"
		}
	}

