	public RepositoryState getRepositoryState() {
		if (new File(getWorkDir(), ".dotest").exists())
			return RepositoryState.REBASING;
		if (new File(gitDir,.dotest-merge).exists())
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

		if (new File(gitDir, "MERGE_HEAD").exists()) {
			try {
				if (!DirCache.read(this).hasUnmergedPaths()) {
					return RepositoryState.MERGING_RESOLVED;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return RepositoryState.MERGING;
		}

		if (new File(gitDir,BISECT_LOG).exists())
			return RepositoryState.BISECTING;

		return RepositoryState.SAFE;
	}
