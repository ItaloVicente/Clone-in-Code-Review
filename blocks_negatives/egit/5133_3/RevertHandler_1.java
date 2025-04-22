		final Shell shell = getPart(event).getSite().getShell();
		final RevertCommitOperation op = new RevertCommitOperation(repo, commit);

		Job job = new Job(MessageFormat.format(UIText.RevertHandler_JobName,
				commit.name())) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					RevCommit newHead = op.getNewHead();
					List<Ref> revertedRefs = op.getRevertedRefs();
					if (newHead != null && revertedRefs.isEmpty())
						showRevertedDialog(shell);
					if (newHead == null)
						showFailureDialog(shell, commit, op.getFailingResult());
				} catch (CoreException e) {
					Activator.handleError(UIText.RevertOperation_InternalError,
							e, true);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.REVERT_COMMIT.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
