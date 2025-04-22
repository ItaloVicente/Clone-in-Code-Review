	private ObjectId insertGitModules(String contents) throws IOException {
		ObjectId blobId = ins.insert(Constants.OBJ_BLOB
				Constants.encode(contents));

		byte[] blobIdBytes = new byte[OBJECT_ID_LENGTH];
		blobId.copyRawTo(blobIdBytes
		byte[] data = concat(encodeASCII("100644 .gitmodules\0")
		ins.insert(Constants.OBJ_TREE
		ins.flush();

		return blobId;
	}

	@Test
	public void testInvalidGitModules() throws Exception {
		String fakeGitmodules = new StringBuilder()
				.append("[submodule \"test\"]\n")
				.append("    path = xlib\n")
				.append("[submodule \"test2\"]\n")
				.append("    path = zlib\n")
				.append("    url = -upayload.sh\n")
				.toString();

		ObjectId blobId = insertGitModules(fakeGitmodules);

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);
		assertEquals(errors.getCorruptObjects().size()

		CorruptObject error = errors.getCorruptObjects().iterator().next();
		assertEquals(error.getId()
		assertEquals(error.getType()
		assertEquals(error.getErrorType()
	}


	@Test
	public void testValidGitModules() throws Exception {
		String fakeGitmodules = new StringBuilder()
				.append("[submodule \"test\"]\n")
				.append("    path = xlib\n")
				.append("[submodule \"test2\"]\n")
				.append("    path = zlib\n")
				.append("    url = ok/path\n")
				.toString();

		insertGitModules(fakeGitmodules);

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);
		assertEquals(errors.getCorruptObjects().size()
	}

