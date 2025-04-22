	private abstract class CommitEditorPageJob extends Job {

		protected final RepositoryCommit commit;

		public CommitEditorPageJob(RepositoryCommit commit) {
			super(MessageFormat.format(UIText.CommitEditorPage_JobName,
					commit.getRevCommit().name()));
			this.commit = commit;
			setRule(CommitEditorPage.this);
		}

		@Override
		public boolean belongsTo(Object family) {
			return CommitEditorPage.this == family;
		}

		protected final void updateUI(IProgressMonitor monitor, Runnable task) {
			ScrolledForm form = getManagedForm().getForm();
			PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
				if (!shouldContinue(monitor, form)) {
					return;
				}
				task.run();
				form.reflow(true);
				form.layout(true, true);
			});
		}

		protected boolean shouldContinue(IProgressMonitor monitor,
				ScrolledForm form) {
			return UIUtils.isUsable(form) && !monitor.isCanceled();
		}
	}

