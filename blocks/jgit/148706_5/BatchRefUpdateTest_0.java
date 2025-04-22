	@Test
	public void packedRefsFileIsSorted() throws IOException {
		assumeTrue(atomic);

		for (int i = 0; i < 2; i++) {
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
		}

		File packed = new File(diskRepo.getDirectory()
		String packedStr = new String(Files.readAllBytes(packed.toPath())

		int a2 = packedStr.indexOf("refs/heads/a1");
		int b1 = packedStr.indexOf("refs/heads/b0");
		assertTrue(a2 <  b1);
	}

