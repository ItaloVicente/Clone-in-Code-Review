					} else
						switch (update) {
						case ConfigConstants.CONFIG_KEY_MERGE:
							MergeCommand merge = new MergeCommand(
									submoduleRepo);
							merge.include(commit);
							merge.setProgressMonitor(monitor);
							merge.setStrategy(strategy);
							merge.call();
							break;
						case ConfigConstants.CONFIG_KEY_REBASE:
							RebaseCommand rebase = new RebaseCommand(
									submoduleRepo);
							rebase.setUpstream(commit);
							rebase.setProgressMonitor(monitor);
							rebase.setStrategy(strategy);
							rebase.call();
							break;
						default:
							DirCacheCheckout co = new DirCacheCheckout(
									submoduleRepo
									commit.getTree());
							co.setFailOnConflict(true);
							co.setProgressMonitor(monitor);
							co.checkout();
							RefUpdate refUpdate = submoduleRepo
									.updateRef(Constants.HEAD
							refUpdate.setNewObjectId(commit);
							refUpdate.forceUpdate();
							if (callback != null) {
								callback.checkingOut(commit
										generator.getPath());
							}
							break;
						}
