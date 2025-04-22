			commitOperation = new CommitOperation(currentRepository,
					commitMessageComponent.getAuthor(),
					commitMessageComponent.getCommitter(),
					commitMessageComponent.getCommitMessage());
		} catch (CoreException e) {
			Activator.handleError(UIText.StagingView_commitFailed, e, true);
			return;
