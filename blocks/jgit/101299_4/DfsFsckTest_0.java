
	@Test
	public void testValidConnectivity() throws Exception {
		ObjectId blobId = ins
				.insert(Constants.OBJ_BLOB

		byte[] blobIdBytes = new byte[OBJECT_ID_LENGTH];
		blobId.copyRawTo(blobIdBytes
		byte[] data = concat(encodeASCII("100644 regular-file\0")
		ObjectId treeId = ins.insert(Constants.OBJ_TREE
		ins.flush();

		RevCommit commit = git.commit().message("0").setTopLevelTree(treeId)
				.create();

		git.update("master"

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);
		assertEquals(errors.getMissingObjects().size()
	}

	@Test
	public void testMissingObject() throws Exception {
		ObjectId blobId = ObjectId
				.fromString("19102815663d23f8b75a47e7a01965dcdc96468c");
		byte[] blobIdBytes = new byte[OBJECT_ID_LENGTH];
		blobId.copyRawTo(blobIdBytes
		byte[] data = concat(encodeASCII("100644 regular-file\0")
		ObjectId treeId = ins.insert(Constants.OBJ_TREE
		ins.flush();

		RevCommit commit = git.commit().message("0").setTopLevelTree(treeId)
				.create();

		git.update("master"

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);
		assertEquals(errors.getMissingObjects().size()
		assertEquals(errors.getMissingObjects().iterator().next()
	}
