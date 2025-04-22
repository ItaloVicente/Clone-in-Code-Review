
	private static class MessageAction extends Action {

		private final String title;

		private final String message;

		public MessageAction(String title, String message) {
			super(title);
			this.title = title;
			this.message = message;
		}

		@Override
		public void run() {
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getModalDialogShellProvider().getShell(), title, message);
		}

	}

	private static class CleanupAction extends RepositoryJobResultAction {

		private final CherryPickResult result;

		private final Runnable retry;

		public CleanupAction(@NonNull Repository repo, String title,
				CherryPickResult result, Runnable retry) {
			super(repo, title);
			this.result = result;
			this.retry = retry;
		}

		@Override
		protected void showResult(Repository repository) {
			Map<String, MergeFailureReason> failed = result.getFailingPaths();
			List<String> failedPaths = new ArrayList<>(failed.size());
			for (Map.Entry<String, MergeFailureReason> entry : failed
					.entrySet()) {
				MergeFailureReason reason = entry.getValue();
				if (reason == null) {
					Activator.showErrorStatus(
							UIText.CherryPickHandler_CherryPickFailedMessage,
							getErrorList(failed));
					return;
				} else {
					switch (reason) {
					case DIRTY_INDEX:
					case DIRTY_WORKTREE:
						failedPaths.add(entry.getKey());
						break;
					default:
						Activator.showErrorStatus(
								UIText.CherryPickHandler_CherryPickFailedMessage,
								getErrorList(failed));
						return;
					}
				}
			}
			if (UIRepositoryUtils.showCleanupDialog(repository, failedPaths,
					UIText.CherryPickHandler_UncommittedFilesTitle,
					PlatformUI.getWorkbench().getModalDialogShellProvider()
							.getShell())) {
				retry.run();
			}
		}
	}
