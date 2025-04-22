	@Test
	public void commitOnlyShouldHandleIgnored() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("subdir/foo"
			writeTrashFile("subdir/bar"
			writeTrashFile(".gitignore"
			git.add().addFilepattern("subdir").call();
			git.commit().setOnly("subdir").setMessage("first commit").call();
		}
	}

