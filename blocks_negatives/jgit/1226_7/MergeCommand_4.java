		}
	}

	private void checkoutNewHead(RevWalk revWalk, RevCommit headCommit,
			RevCommit newHeadCommit) throws IOException,
			CheckoutConflictException {
		GitIndex index = repo.getIndex();

		File workDir = repo.getWorkTree();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(repo,
					workDir, repo.mapTree(headCommit.getTree()), index,
					repo.mapTree(newHeadCommit.getTree()));
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
				throw new CheckoutConflictException(
						JGitText.get().couldNotCheckOutBecauseOfConflicts,
						workDirCheckout.getConflicts(), e);
			}
			index.write();
