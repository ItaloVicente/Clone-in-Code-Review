		RepositoryCommit commit = AdapterUtils.adapt(getEditor(),
				RepositoryCommit.class);
		if (commit == null) {
			return;
		}
		if (commit.getRevCommit().getParentCount() > 1) {
			setInput(new DiffEditorInput(commit, null));
			return;
		}
