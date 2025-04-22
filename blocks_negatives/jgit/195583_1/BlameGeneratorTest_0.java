	public void testSingleBlame() throws Exception {

		/**
		 * <pre>
		 * (ts) 	OTHER_FILE			INTERESTING_FILE
		 * 1 		a
		 * 2	 	a, b
		 * 3							1, 2				c1 <--
		 * 4	 	a, b, c										 |
		 * 5							1, 2, 3				c2---
		 * </pre>
		 */
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {
			writeTrashFile(OTHER_FILE, join("a"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE, join("a", "b"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE, join("1", "2"));
			git.add().addFilepattern(INTERESTING_FILE).call();
