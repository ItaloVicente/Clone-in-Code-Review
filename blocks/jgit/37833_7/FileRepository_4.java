		if (!bare) {
			File workTree = getWorkTree();
			if (!getDirectory().getParentFile().equals(workTree)) {
				cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_WORKTREE
								.getAbsolutePath());
				LockFile dotGitLockFile = new LockFile(new File(workTree
						Constants.DOT_GIT)
				try {
					if (dotGitLockFile.lock()) {
						dotGitLockFile.write(Constants.encode(Constants.GITDIR
								+ getDirectory().getAbsolutePath()));
						dotGitLockFile.commit();
					}
				} finally {
					dotGitLockFile.unlock();
				}
			}
		}
