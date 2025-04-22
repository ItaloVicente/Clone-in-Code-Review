	public void testSingleBlame() throws Exception {

		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {
			writeTrashFile(OTHER_FILE
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
