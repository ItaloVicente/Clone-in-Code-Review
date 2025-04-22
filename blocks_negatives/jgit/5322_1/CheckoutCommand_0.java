			DirCacheCheckout dco = new DirCacheCheckout(repo, headTree,
					repo.lockDirCache(), newCommit.getTree());
			dco.setFailOnConflict(true);
			try {
				dco.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
				status = new CheckoutResult(Status.CONFLICTS, dco
						.getConflicts());
				throw new CheckoutConflictException(dco.getConflicts(), e);
