			commitPage = new CommitEditorPage(this);
			addPage(commitPage);
			if (getCommit().getRevCommit().getParentCount() == 1) {
				diffPage = new DiffEditorPage(this);
				addPage(diffPage);
			}
