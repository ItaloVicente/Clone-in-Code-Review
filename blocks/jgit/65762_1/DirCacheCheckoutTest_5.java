		try (Git git = new Git(db)) {
			TestRepository<Repository> db_t = new TestRepository<Repository>(db);
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			assertFalse(new File(db.getWorkTree()
			git.checkout().setName("master").call();
			assertTrue(new File(db.getWorkTree()
		}
