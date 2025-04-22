	@Test
	public void testCheckoutWithMissingWorkingTreeFile() throws Exception {
		Git git = new Git(db);
		File fileA = writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("modify file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(fileA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
		assertEquals("Hello world a"
	}

	static private void assertEquals(Object expected
		Assert.assertEquals(expected
	}

