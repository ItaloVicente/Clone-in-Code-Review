		@Override
		public IStatus getStatus(Object element) {
			if (element instanceof CommitEditorInput) {
				RepositoryCommit commit = ((CommitEditorInput) element)
						.getCommit();
				if (commit != null && commit.getRevCommit() != null
						&& commit.getRevCommit().getParentCount() > 1) {
					return Activator.error(
							UIText.DiffEditorPage_WarningNoDiffForMerge, null);
				}
			}
			return Status.OK_STATUS;
		}

