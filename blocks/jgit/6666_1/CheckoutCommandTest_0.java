	@Test
	public void testCheckoutOfFileWithInexistentParentDir() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		writeTrashFile("dir/b.txt"
		git.add().addFilepattern("dir/a.txt").addFilepattern("dir/b.txt")
				.call();
		git.commit().setMessage("Added dir").call();

		File dir = new File(db.getWorkTree()
		FileUtils.delete(dir

		git.checkout().addPath("dir/a.txt").call();
		assertTrue(a.exists());
	}

