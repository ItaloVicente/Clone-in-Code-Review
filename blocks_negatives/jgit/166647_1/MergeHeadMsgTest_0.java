		assertEquals(db.readMergeHeads().size(), 2);
		assertEquals(db.readMergeHeads().get(0), ObjectId.zeroId());
		assertEquals(db.readMergeHeads().get(1), ObjectId.fromString(sampleId));
		db.writeMergeHeads(Collections.<ObjectId> emptyList());
		assertEquals(read(new File(db.getDirectory(), "MERGE_HEAD")), "");
		assertEquals(db.readMergeHeads(), null);
