					if (null == update) {
						DirCacheCheckout co = new DirCacheCheckout(
								submoduleRepo
								commit.getTree());
						co.setFailOnConflict(true);
						co.setProgressMonitor(monitor);
						co.checkout();
						RefUpdate refUpdate = submoduleRepo.updateRef(
								Constants.HEAD
						refUpdate.setNewObjectId(commit);
						refUpdate.forceUpdate();
						if (callback != null) {
							callback.checkingOut(commit
									generator.getPath());
						}
					} else switch (update) {
					case ConfigConstants.CONFIG_KEY_MERGE:
