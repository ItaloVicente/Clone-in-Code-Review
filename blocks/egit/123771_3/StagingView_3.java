			StyledString title = GitLabels.getStyledLabelSafe(repository);
			if (repository.getRepositoryState() == RepositoryState.SAFE) {
				try {
					Ref head = repository.exactRef(Constants.HEAD);
					if (head != null && head.isSymbolic()
							&& head.getObjectId() == null) {
						title.append(UIText.StagingView_InitialCommitText);
					}
				} catch (IOException e) {
					Activator.logError(e.getLocalizedMessage(), e);
				}
			}
			form.setText(title.toString());
