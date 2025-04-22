		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();

			assertTrue(new File(db.getWorkTree()
			createBranch(first

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/branch1");

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit third = git.commit().setMessage("third commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()

			MergeResult result = git.merge()
					.include(db.exactRef("refs/heads/branch1"))
					.setSquash(true)
					.call();

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.CONFLICTING
					result.getMergeStatus());
			assertNull(result.getNewHead());
			assertEquals(second

			assertEquals(
					"Squashed commit of the following:\n\ncommit "
							+ third.getName()
							+ "\nAuthor: "
							+ third.getAuthorIdent().getName()
							+ " <"
							+ third.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   "
							+ dateFormatter.formatDate(third
									.getAuthorIdent()) + "\n\n\tthird commit\n"
					db.readSquashCommitMsg());
			assertEquals("\nConflicts:\n\tfile2\n"

			Status stat = git.status().call();
			assertEquals(Sets.of("file2")
		}
