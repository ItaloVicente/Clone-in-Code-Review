		}

		stageThread = null;
	}

	private void stage(final IStructuredSelection selection) {
		awaitStageThread();

		stageThread = new Thread() {
			public void run() {
				asyncLockUI();
				try {
					Git git = new Git(currentRepository);
					AddCommand add = null;
					RmCommand rm = null;
					Iterator iterator = selection.iterator();
					while (iterator.hasNext()) {
						StagingEntry entry = (StagingEntry) iterator.next();
						switch (entry.getState()) {
						case ADDED:
						case CHANGED:
						case REMOVED:
							break;
						case CONFLICTING:
						case MODIFIED:
						case PARTIALLY_MODIFIED:
						case UNTRACKED:
							if (add == null)
								add = git.add();
							add.addFilepattern(entry.getPath());
							break;
						case MISSING:
							if (rm == null)
								rm = git.rm();
							rm.addFilepattern(entry.getPath());
							break;
						}
					}

					if (add != null)
						try {
							add.call();
						} catch (NoFilepatternException e1) {
						} catch (Exception e2) {
							Activator.error(e2.getMessage(), e2);
						}
					if (rm != null)
						try {
							rm.call();
						} catch (NoFilepatternException e) {
						} catch (Exception e2) {
							Activator.error(e2.getMessage(), e2);
						}
				} finally {
					asyncUnlockUI();
				}
