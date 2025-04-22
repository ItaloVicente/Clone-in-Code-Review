		form.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (form.isDisposed())
					return;
				StagingViewUpdate update = new StagingViewUpdate(repository, indexDiff, Collections.<String> emptyList());
				unstagedTableViewer.setInput(update);
				stagedTableViewer.setInput(update);
				commitAction.setEnabled(repository.getRepositoryState()
						.canCommit());
				form.setText(StagingView.getRepositoryName(repository));
				updateCommitMessageComponent(repositoryChanged);
				updateSectionText();
			}

		});

