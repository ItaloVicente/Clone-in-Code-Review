			message = MessageFormat.format(
					UIText.CherryPickHandler_ConfirmMessage, Integer.valueOf(1),
					repository.getBranch());
		} catch (IOException e) {
			throw new ExecutionException(
					"Exception obtaining current repository branch", e); //$NON-NLS-1$
		}

		String launchMessage = getLaunchMessage(repository);
		if (launchMessage != null) {
		}
		final String question = message;
		shell.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				ConfirmCherryPickDialog dialog = new ConfirmCherryPickDialog(
						shell, question, repository, Arrays.asList(commit));
				int result = dialog.open();
				confirmed.set(result == Window.OK);
			}
		});
		return confirmed.get();
	}

	private static class ConfirmCherryPickDialog extends MessageDialog {

		private RepositoryCommit[] commits;

		public ConfirmCherryPickDialog(Shell parentShell,
				String message, Repository repository, List<RevCommit> revCommits) {
			super(parentShell, UIText.CherryPickHandler_ConfirmTitle, null,
					message, MessageDialog.CONFIRM, new String[] {
							UIText.CherryPickHandler_cherryPickButtonLabel,
							IDialogConstants.CANCEL_LABEL }, 0);
			setShellStyle(getShellStyle() | SWT.RESIZE);

			List<RepositoryCommit> repoCommits = new ArrayList<>();
			for (RevCommit commit : revCommits)
				repoCommits.add(new RepositoryCommit(repository, commit));
			this.commits = repoCommits.toArray(new RepositoryCommit[0]);
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			area.setLayoutData(GridDataFactory.fillDefaults().grab(true, true)
					.create());
			area.setLayout(new FillLayout());

			TreeViewer treeViewer = new TreeViewer(area);
			treeViewer.setContentProvider(new ContentProvider());
			treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
					new WorkbenchLabelProvider()));
			treeViewer.setInput(commits);

			return area;
		}

		private static class ContentProvider extends WorkbenchContentProvider {

			@Override
			public Object[] getElements(final Object element) {
				return (Object[]) element;
			}

			@Override
			public Object[] getChildren(Object element) {
				if (element instanceof RepositoryCommit)
					return ((RepositoryCommit) element).getDiffs();
				return super.getChildren(element);
			}
		}
	}

	private static IStatus getErrorList(
			Map<String, MergeFailureReason> failingPaths) {
		MultiStatus result = new MultiStatus(Activator.getPluginId(),
				IStatus.ERROR,
				UIText.CherryPickHandler_CherryPickFailedMessage, null);
		for (Entry<String, MergeFailureReason> entry : failingPaths.entrySet()) {
			String path = entry.getKey();
			String reason = getReason(entry.getValue());
			String errorMessage = NLS.bind(
					UIText.CherryPickHandler_ErrorMsgTemplate, path, reason);
			result.add(Activator.createErrorStatus(errorMessage));
		}
		return result;
	}

	private static String getReason(MergeFailureReason mergeFailureReason) {
		switch (mergeFailureReason) {
		case COULD_NOT_DELETE:
			return UIText.CherryPickHandler_CouldNotDeleteFile;
		case DIRTY_INDEX:
			return UIText.CherryPickHandler_IndexDirty;
		case DIRTY_WORKTREE:
			return UIText.CherryPickHandler_WorktreeDirty;
		}
		return UIText.CherryPickHandler_unknown;
	}

	/**
	 * Displays a simple warning dialog with the given title and message.
	 */
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

	/**
	 * If a cherry-pick failure was due to a dirty index or working tree only,
	 * show a dialog giving the user the opportunity to clean-up, and then
	 * re-try the cherry-pick. If there were any other failures, show an error
	 * dialog and abort.
	 */
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
