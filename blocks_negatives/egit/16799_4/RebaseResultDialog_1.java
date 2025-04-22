				final RebaseOperation op = new RebaseOperation(repo,
						Operation.ABORT);
				JobUtil.scheduleUserJob(op,
						UIText.RebaseResultDialog_JobNameAbortRebase,
						JobFamilies.REBASE, new JobChangeAdapter() {
							@Override
							public void done(IJobChangeEvent event) {
								show(op.getResult(), repo);
							}
						});
