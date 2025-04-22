	@Test
	public void testCheckout() throws Exception {
		int i = 1;
		do {
			File gitDir = createUniqueTestGitDir(false);
			FileRepository db1 = new FileRepository(gitDir);

			File testFile = new File(db1.getWorkTree()
			testFile.createNewFile();
			write(testFile
			File folder = new File(db1.getWorkTree()
			folder.mkdir();
			File folderFile = new File(folder
			folderFile.createNewFile();
			write(folderFile

			Git git1 = new Git(db1);
			git1.add().addFilepattern(".").call();
			git1.commit().setMessage("Initial commit").call();

			write(testFile

			git1.add().addFilepattern("test.txt").call();
			git1.add().setUpdate(true).addFilepattern("test.txt").call();

			git1.reset().setRef(Constants.HEAD).addPath("test.txt").call();

			git1.checkout().addPath("test.txt").call();

			if (git1.status().call().getModified().size() != 0) {
				fail(String.format("failed after %d attempt(s)"
			}
			i++;
		} while (true);
	}
