
	@Test
	public void testFilesShouldBeCleanedInSubSubFolders()
			throws IOException
		writeTrashFile(".gitignore"
				"/ignored-dir\n/sub-noclean/Ignored.txt\n/this_is_ok\n/this_is/not_ok\n");
		git.add().addFilepattern(".gitignore").call();
		git.commit().setMessage("adding .gitignore").call();
		writeTrashFile("this_is_ok/more/subdirs/file.txt"
		writeTrashFile("this_is/not_ok/more/subdirs/file.txt"
		git.clean().setCleanDirectories(true).setIgnore(false).call();
	}
