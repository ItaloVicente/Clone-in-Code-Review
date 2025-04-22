public class AbortRebaseCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	public Object execute(ExecutionEvent event) throws ExecutionException {

		RepositoryTreeNode node = getSelectedNodes(event).get(0);

		final Repository repository = node.getRepository();

		String jobname = UIText.AbortRebaseCommand_JobName;
		final RebaseOperation rebase = new RebaseOperation(repository,
				Operation.ABORT);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					rebase.execute(monitor);
				} catch (final CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(rebase.getSchedulingRule());
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent cevent) {
				IStatus result = cevent.getJob().getResult();
				if (result.getSeverity() == IStatus.CANCEL) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog
									.openInformation(
											shell,
											UIText.AbortRebaseCommand_CancelDialogTitle,
											UIText.AbortRebaseCommand_CancelDialogMessage);
						}
					});
				} else if (result.isOK()) {
					RebaseResultDialog.show(rebase.getResult(), repository);
				}
			}
		});
		job.schedule();
		return null;
