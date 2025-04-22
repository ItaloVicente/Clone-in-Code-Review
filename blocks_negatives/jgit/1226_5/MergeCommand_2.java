	}

	private void checkoutNewHead(RevWalk revWalk, RevCommit headCommit,
			RevCommit newHeadCommit) throws IOException,
			CheckoutConflictException {
		GitIndex index = repo.getIndex();
