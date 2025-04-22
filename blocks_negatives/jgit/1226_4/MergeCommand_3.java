		File workDir = repo.getWorkTree();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(repo,
					workDir, headCommit.asCommit(revWalk).getTree(), index,
					newHeadCommit.asCommit(revWalk).getTree());
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
				throw new CheckoutConflictException(
						JGitText.get().couldNotCheckOutBecauseOfConflicts,
						workDirCheckout.getConflicts(), e);
			}
			index.write();
		}
