		checkoutBranch("refs/heads/topic");
		RebaseResult res = git.rebase().setUpstream("refs/heads/master")
				.setPreserveMerges(true).call();
		if (testConflict) {
			assertEquals(Status.STOPPED, res.getStatus());
			assertEquals(Collections.singleton("conflict"), git.status().call()
					.getConflicting());
			writeTrashFile("conflict", "e");
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();
