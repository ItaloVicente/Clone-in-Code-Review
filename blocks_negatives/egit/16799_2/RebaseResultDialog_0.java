				final RebaseOperation op = new RebaseOperation(repo,
						Operation.SKIP);
				JobUtil.scheduleUserJob(op,
						UIText.RebaseResultDialog_JobNameSkipCommit,
						JobFamilies.REBASE, new JobChangeAdapter() {
							@Override
							public void done(IJobChangeEvent event) {
								show(op.getResult(), repo);
							}
						});
