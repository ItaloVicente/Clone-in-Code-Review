						DirCacheCheckout co = new DirCacheCheckout(
								submoduleRepo, submoduleRepo.lockDirCache(),
								commit.getTree());
						co.setFailOnConflict(true);
						co.setProgressMonitor(monitor);
						co.checkout();
