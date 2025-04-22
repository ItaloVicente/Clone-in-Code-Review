	@Test
	public void commitEmptyCommits() throws Exception {
		try (Git git = new Git(db)) {

			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit initial = git.commit().setMessage("initial commit")
					.call();

			RevCommit emptyFollowUp = git.commit()
					.setAuthor("New Author"
					.setMessage("no change").call();

			assertNotEquals(initial.getId()
			assertEquals(initial.getTree().getId()
					emptyFollowUp.getTree().getId());

			try {
				git.commit().setAuthor("New Author"
						.setMessage("again no change").setAllowEmpty(false)
						.call();
				fail("Didn't get the expected EmtpyCommitException");
			} catch (EmtpyCommitException e) {
			}
		}
	}

