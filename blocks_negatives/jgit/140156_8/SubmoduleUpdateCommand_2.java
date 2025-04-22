					if (ConfigConstants.CONFIG_KEY_MERGE.equals(update)) {
						MergeCommand merge = new MergeCommand(submoduleRepo);
						merge.include(commit);
						merge.setProgressMonitor(monitor);
						merge.setStrategy(strategy);
						merge.call();
					} else if (ConfigConstants.CONFIG_KEY_REBASE.equals(update)) {
						RebaseCommand rebase = new RebaseCommand(submoduleRepo);
						rebase.setUpstream(commit);
						rebase.setProgressMonitor(monitor);
						rebase.setStrategy(strategy);
						rebase.call();
					} else {
						DirCacheCheckout co = new DirCacheCheckout(
								submoduleRepo, submoduleRepo.lockDirCache(),
								commit.getTree());
						co.setFailOnConflict(true);
						co.setProgressMonitor(monitor);
						co.checkout();
						RefUpdate refUpdate = submoduleRepo.updateRef(
								Constants.HEAD, true);
						refUpdate.setNewObjectId(commit);
						refUpdate.forceUpdate();
						if (callback != null) {
							callback.checkingOut(commit,
									generator.getPath());
						}
					}
