			try {
				if (isCheckoutIndex())
					checkoutPathsFromIndex(treeWalk, dc);
				else {
					RevCommit commit = revWalk.parseCommit(getStartPointObjectId());
					checkoutPathsFromCommit(treeWalk, dc, commit);
				}
			} finally {
				treeWalk.release();
