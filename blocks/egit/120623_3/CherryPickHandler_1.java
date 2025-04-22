			@Override
			protected IAction getAction() {
				RevCommit newHead = result.getNewHead();
				if (newHead == null) {
					switch (result.getStatus()) {
					case CONFLICTING:
						return new MessageAction(
								UIText.CherryPickHandler_CherryPickConflictsTitle,
								UIText.CherryPickHandler_CherryPickConflictsMessage);
					case FAILED:
						if (!withCleanup) {
							return new RepositoryJobResultAction(repo,
									UIText.CherryPickHandler_CherryPickFailedMessage) {

								@Override
								protected void showResult(
										Repository repository) {
									Activator.showErrorStatus(
											UIText.CherryPickHandler_CherryPickFailedMessage,
											getErrorList(
													result.getFailingPaths()));
								}
							};
						}
						return new CleanupAction(repo,
								UIText.CherryPickHandler_UncommittedFilesTitle,
								result, () -> doCherryPick(repo, commit,
										parentIndex, false));
					case OK:
						return null;
					}
				} else if (result.getCherryPickedRefs().isEmpty()) {
					return new MessageAction(
							UIText.CherryPickHandler_NoCherryPickPerformedTitle,
							UIText.CherryPickHandler_NoCherryPickPerformedMessage);
				}
				return null;
			}

