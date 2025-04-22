	@Test
	public void testCheckoudWithEmptyIndexDoesntOverwrite() throws Exception {
		try (Git git = new Git(db)) {
			TestRepository<Repository> db_t = new TestRepository<>(db);
			BranchBuilder master = db_t.branch("master");
			RevCommit mergeCommit = master.commit()
					.add("p/x"
					.message("m0").create();
			master.commit().add("p/x"
			git.checkout().setName("master").call();

			git.rm().addFilepattern("p").call();
			writeTrashFile("p"

			git.checkout().setName(mergeCommit.getName()).call();

			assertEquals(""
			assertEquals("important data"
		}
	}

