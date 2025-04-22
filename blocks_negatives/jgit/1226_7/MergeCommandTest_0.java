	private void checkoutBranch(String branchName) throws Exception  {
		File workDir = db.getWorkTree();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(db,
					workDir, db.mapTree(Constants.HEAD),
					db.getIndex(), db.mapTree(branchName));
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (CheckoutConflictException e) {
				throw new JGitInternalException(
						"Couldn't check out because of conflicts", e);
			}
		}

