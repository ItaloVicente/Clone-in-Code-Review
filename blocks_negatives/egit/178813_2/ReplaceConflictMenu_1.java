			if (toRemove.isEmpty()) {
				DiscardChangesOperation operation = new DiscardChangesOperation(
						repository, toCheckout);
				operation.setStage(stage);
				JobUtil.scheduleUserWorkspaceJob(operation,
						UIText.DiscardChangesAction_discardChanges,
						JobFamilies.DISCARD_CHANGES);
			} else {
				IEGitOperation operation = new IEGitOperation() {

					@Override
					public ISchedulingRule getSchedulingRule() {
						return RuleUtil.getRule(repository);
					}

					@Override
					public void execute(IProgressMonitor monitor)
							throws CoreException {
						IWorkspaceRunnable action = new IWorkspaceRunnable() {

							@Override
							public void run(IProgressMonitor progress)
									throws CoreException {
								ResourceUtil.saveLocalHistory(repository);
								try (Git git = new Git(repository)) {
									if (!toCheckout.isEmpty()) {
										CheckoutCommand checkout = git
												.checkout().setProgressMonitor(
														new EclipseGitProgressTransformer(
																progress));

										checkout
												.setStage(stage == Stage.OURS
														? CheckoutCommand.Stage.OURS
														: CheckoutCommand.Stage.THEIRS);
										for (String path : toCheckout) {
											checkout.addPath(path);
										}
										checkout.call();
									}
									if (!toRemove.isEmpty()) {
										RmCommand rm = git.rm();
										for (String path : toRemove) {
											rm.addFilepattern(path);
										}
										rm.call();
									}
								} catch (GitAPIException e) {
									throw new CoreException(
											Activator.createErrorStatus(
													e.getLocalizedMessage(),
													e));
								}
							}
						};
						ResourcesPlugin.getWorkspace().run(action,
								getSchedulingRule(), IWorkspace.AVOID_UPDATE,
								monitor);
					}
				};
				JobUtil.scheduleUserWorkspaceJob(operation,
						UIText.DiscardChangesAction_discardChanges,
						JobFamilies.DISCARD_CHANGES);

			}
