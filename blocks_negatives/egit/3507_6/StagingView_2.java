				if (!unstagedTableViewer.getTable().isDisposed())
					unstagedTableViewer.setInput(new Object[] { repository, indexDiff });
				if (!stagedTableViewer.getTable().isDisposed())
					stagedTableViewer.setInput(new Object[] { repository, indexDiff });
				if (!commitButton.isDisposed())
					commitButton.setEnabled(repository.getRepositoryState().canCommit());
				if (!repositoryLabel.isDisposed()) {
					repositoryLabel.setText(StagingView.getRepositoryName(repository));
				}
