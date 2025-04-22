		Git git = new Git(db);

		writeTrashFile("file1", "file1");
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree(), "file1").exists());
		createBranch(first, "refs/heads/branch1");

		writeTrashFile("file2", "master");
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree(), "file2").exists());

		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file2", "branch");
		git.add().addFilepattern("file2").call();
		RevCommit third = git.commit().setMessage("third commit").call();
		assertTrue(new File(db.getWorkTree(), "file2").exists());

		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree(), "file1").exists());
		assertTrue(new File(db.getWorkTree(), "file2").exists());

		MergeResult result = git.merge()
				.include(db.exactRef("refs/heads/branch1"))
				.setSquash(true)
				.call();

		assertTrue(new File(db.getWorkTree(), "file1").exists());
		assertTrue(new File(db.getWorkTree(), "file2").exists());
		assertEquals(MergeResult.MergeStatus.CONFLICTING,
				result.getMergeStatus());
		assertNull(result.getNewHead());
		assertEquals(second, db.resolve(Constants.HEAD + "^{commit}"));

		assertEquals(
				"Squashed commit of the following:\n\ncommit "
						+ third.getName()
						+ "\nAuthor: "
						+ third.getAuthorIdent().getName()
						+ " <"
						+ third.getAuthorIdent().getEmailAddress()
						+ ">\nDate:   "
						+ dateFormatter.formatDate(third
								.getAuthorIdent()) + "\n\n\tthird commit\n",
				db.readSquashCommitMsg());
		assertEquals("\nConflicts:\n\tfile2\n", db.readMergeCommitMsg());

		Status stat = git.status().call();
		assertEquals(Sets.of("file2"), stat.getConflicting());
