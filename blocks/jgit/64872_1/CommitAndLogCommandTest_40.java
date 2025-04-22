		try (Git git = new Git(db)) {
			git.commit().setAmend(true).setMessage("first commit").call();

			Iterable<RevCommit> commits = git.log().call();
			int c = 0;
			for (RevCommit commit : commits) {
				assertEquals("first commit"
				c++;
			}
			assertEquals(1
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("commit (amend):"));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("commit (amend):"));
