				if (form.isDisposed())
					return;
				unstagedTableViewer.setInput(new Object[] { repository,
						indexDiff });
				stagedTableViewer
						.setInput(new Object[] { repository, indexDiff });
				commitAction.setEnabled(repository.getRepositoryState()
						.canCommit());
				form.setText(StagingView.getRepositoryName(repository));
				updateCommitMessageComponent(repositoryChanged);
				updateSectionText();
