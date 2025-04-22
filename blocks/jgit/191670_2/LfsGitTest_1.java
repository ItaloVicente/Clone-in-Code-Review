	@Test
	public void testBranchSwitch() throws Exception {
		git.branchCreate().setName("abranch").call();
		git.checkout().setName("abranch").call();
		File aFile = writeTrashFile("a.bin"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("acommit").call();
		git.checkout().setName("master").call();
		git.branchCreate().setName("bbranch").call();
		git.checkout().setName("bbranch").call();
		File bFile = writeTrashFile("b.bin"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("bcommit").call();
		git.checkout().setName("abranch").call();
		checkFile(aFile
		git.checkout().setName("bbranch").call();
		checkFile(bFile
	}

