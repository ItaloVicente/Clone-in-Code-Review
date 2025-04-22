			RefUpdate u = repository.updateRef(Constants.HEAD, detach);
			Result res;
			if (detach) {
				u.setNewObjectId(newCommit.getCommitId());
				u.setRefLogMessage(NLS.bind(
						CoreText.BranchOperation_checkoutMovingTo, newCommit
								.getCommitId().toString()), false);
				res = u.forceUpdate();
			} else {
				u.setRefLogMessage(NLS.bind(
						CoreText.BranchOperation_checkoutMovingTo, refName),
						false);
				res = u.link(refName);
			}
			switch (res) {
