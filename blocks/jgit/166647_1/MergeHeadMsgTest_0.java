		assertEquals(repository.readMergeHeads().size()
		assertEquals(repository.readMergeHeads().get(0)
		assertEquals(repository.readMergeHeads().get(1)
		repository.writeMergeHeads(Collections.<ObjectId> emptyList());
		assertEquals(read(new File(repository.getDirectory()
		assertEquals(repository.readMergeHeads()
