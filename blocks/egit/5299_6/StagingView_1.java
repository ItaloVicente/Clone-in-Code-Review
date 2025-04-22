				final IndexDiffData indexDiff = doReload(repository);
				final StagingViewUpdate update = new StagingViewUpdate(currentRepository, indexDiff, null);
				unstagedTableViewer.setInput(update);
				stagedTableViewer.setInput(update);
				enableCommitWidgets(true);
				commitAction.setEnabled(repository.getRepositoryState()
						.canCommit());
				form.setText(StagingView.getRepositoryName(repository));
				updateCommitMessageComponent(repositoryChanged);
				updateSectionText();
