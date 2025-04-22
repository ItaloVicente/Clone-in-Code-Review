
	@Test
	public void testSquash() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();
		new Git(db).branchCreate().setName("side").call();
		writeTrashFile("file1"
		new Git(db).add().addFilepattern("file1").call();
		new Git(db).commit().setMessage("file1 commit").call();
		writeTrashFile("file2"
		new Git(db).add().addFilepattern("file2").call();
		new Git(db).commit().setMessage("file2 commit").call();
		new Git(db).checkout().setName("side").call();
		writeTrashFile("side"
		new Git(db).add().addFilepattern("side").call();
		new Git(db).commit().setMessage("side commit").call();

		assertArrayEquals(
				new String[] { "Squash commit -- not updating HEAD"
						"Automatic merge went well; stopped before committing as requested"
						"" }
				execute("git merge master --squash"));
	}
