				dc = repo.lockDirCache();
				dco = new DirCacheCheckout(repo
						newCommit.getTree());
				dco.setFailOnConflict(true);
				try {
					dco.checkout();
				} catch (CheckoutConflictException e) {
					status = new CheckoutResult(Status.CONFLICTS
							dco.getConflicts());
					throw e;
				}
			} finally {
				if (dc != null)
					dc.unlock();
