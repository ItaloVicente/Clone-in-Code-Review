		Git git = new Git(db);

		writeTrashFile("file1", "file1");
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree(), "file1").exists());
		createBranch(first, "refs/heads/branch1");

		writeTrashFile("file2", "file2");
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree(), "file2").exists());

		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file3", "file3");
		git.add().addFilepattern("file3").call();
		RevCommit third = git.commit().setMessage("third commit").call();
		assertTrue(new File(db.getWorkTree(), "file3").exists());

		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree(), "file1").exists());
		assertTrue(new File(db.getWorkTree(), "file2").exists());
		assertFalse(new File(db.getWorkTree(), "file3").exists());

		MergeResult result = git.merge()
				.include(db.exactRef("refs/heads/branch1"))
				.setSquash(true)
				.call();

		assertTrue(new File(db.getWorkTree(), "file1").exists());
		assertTrue(new File(db.getWorkTree(), "file2").exists());
		assertTrue(new File(db.getWorkTree(), "file3").exists());
		assertEquals(MergeResult.MergeStatus.MERGED_SQUASHED,
				result.getMergeStatus());
		assertEquals(second, result.getNewHead()); // HEAD didn't move
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
		assertNull(db.readMergeCommitMsg());

		Status stat = git.status().call();
		assertEquals(Sets.of("file3"), stat.getAdded());
