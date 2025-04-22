	public RepositoryState getRepositoryState() {
		if (isBare() || getDirectory() == null)
			return RepositoryState.BARE;

		if (new File(getWorkTree(), ".dotest").exists())
			return RepositoryState.REBASING;
		if (new File(getDirectory(), ".dotest-merge").exists())
			return RepositoryState.REBASING_INTERACTIVE;

		if (new File(getDirectory(),rebase-apply/rebasing).exists())
			return RepositoryState.REBASING_REBASING;
		if (new File(getDirectory(),rebase-apply/applying).exists())
			return RepositoryState.APPLY;
		if (new File(getDirectory(),rebase-apply).exists())
			return RepositoryState.REBASING;

		if (new File(getDirectory(),rebase-merge/interactive).exists())
			return RepositoryState.REBASING_INTERACTIVE;
		if (new File(getDirectory(),rebase-merge).exists())
			return RepositoryState.REBASING_MERGE;

		if (new File(getDirectory(), Constants.MERGE_HEAD).exists()) {
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.MERGING_RESOLVED;
				}
			} catch (IOException e) {
			}
			return RepositoryState.MERGING;
		}

		if (new File(getDirectory(), "BISECT_LOG").exists())
			return RepositoryState.BISECTING;

		if (new File(getDirectory(), Constants.CHERRY_PICK_HEAD).exists()) {
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.CHERRY_PICKING_RESOLVED;
				}
			} catch (IOException e) {
			}

			return RepositoryState.CHERRY_PICKING;
		}

		return RepositoryState.SAFE;
	}
