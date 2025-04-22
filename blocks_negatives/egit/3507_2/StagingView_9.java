				if (!unstagedTableViewer.getTable().isDisposed())
					unstagedTableViewer.setInput(new Object[] { repository, indexDiff });
				if (!stagedTableViewer.getTable().isDisposed())
					stagedTableViewer.setInput(new Object[] { repository, indexDiff });
				if (!commitButton.isDisposed())
					commitButton.setEnabled(repository.getRepositoryState().canCommit());
				if (!authorText.isDisposed())
					updateAuthorAndCommitter(repository);
				if (!commitMessageText.isDisposed())
					updateCommitMessage(repository);
				if (!repositoryLabel.isDisposed()) {
					repositoryLabel.setText(StagingView.getRepositoryName(repository));
				}
