			commitBuilder = new CommitBuilder();
			commitBuilder.setAuthor(author);
			commitBuilder.setCommitter(committer);
			commitBuilder.setMessage("foo#2");
			commitBuilder.setTreeId(insertId);
			commitBuilder.setParentId(firstCommitId);
			ObjectId commitId = newObjectInserter.insert(commitBuilder);

			if (!secondCheckout)
				git.checkout().setStartPoint(revWalk.parseCommit(firstCommitId))
						.setName("refs/heads/master").setCreateBranch(true).call();
			try {
				if (secondCheckout) {
					git.checkout().setStartPoint(revWalk.parseCommit(commitId))
							.setName("refs/heads/master").setCreateBranch(true)
							.call();
				} else {
					git.branchCreate().setName("refs/heads/next")
							.setStartPoint(commitId.name()).call();
					git.checkout().setName("refs/heads/next")
							.call();
				}
				if (!good)
					fail("Checkout of Tree " + Arrays.asList(path) + " should fail");
			} catch (InvalidPathException e) {
				if (good)
					throw e;
				assertTrue(e.getMessage().startsWith("Invalid path"));
