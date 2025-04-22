					JobFamilies.GITFLOW_FAMILY, new JobChangeAdapter() {
						@Override
						public void done(IJobChangeEvent jobChangeEvent) {
							if (jobChangeEvent.getResult().isOK()) {
								postMerge(gfRepo, featureBranch, squash,
										operation.getMergeResult());
							}
						}
					});
