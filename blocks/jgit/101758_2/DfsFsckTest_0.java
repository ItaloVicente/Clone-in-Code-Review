
	@Test
	public void testNonCommitHead() throws Exception {
		RevCommit commit0 = git.commit().message("0").create();
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append(commit0.getName());
		b.append('\n');
		b.append("type commit\n");
		b.append("tag test-tag\n");
		b.append("tagger A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		ObjectId tagId = ins.insert(Constants.OBJ_TAG
		ins.flush();

		git.update("master"

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);
		assertEquals(errors.getCorruptObjects().size()
		assertEquals(errors.getNonCommitHeads().size()
		assertEquals(errors.getNonCommitHeads().iterator().next()
				"refs/heads/master");
	}

