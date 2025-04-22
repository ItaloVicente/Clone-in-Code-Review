				if (unstagedTableViewer.getTable().isDisposed())
					return;

				unstagedTableViewer.setInput(new Object[] { repository,
						indexDiff });
				stagedTableViewer
						.setInput(new Object[] { repository, indexDiff });
				commitAction.setEnabled(repository.getRepositoryState()
						.canCommit());
				updateAuthorAndCommitter(repository);
				updateCommitMessage(repository);
				form.setText(getRepositoryName(repository));
