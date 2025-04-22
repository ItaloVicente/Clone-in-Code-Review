					if (mode != CheckoutMode.NOCHECKOUT) {
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IWorkspaceRunnable operation = new IWorkspaceRunnable() {

							@Override
							public void run(IProgressMonitor innerMonitor)
									throws CoreException {
								SubMonitor innerProgress = SubMonitor
										.convert(innerMonitor, steps);
								switch (mode) {
								case CHECKOUT_FETCH_HEAD:
									checkout(commit.name(),
											innerProgress.newChild(1));
									break;
								case CREATE_TAG:
									createTag(spec, textForTag, commit,
											innerProgress.newChild(1));
									checkout(commit.name(),
											innerProgress.newChild(1));
									break;
								case CREATE_BRANCH:
									createBranch(textForBranch,
											doCheckoutNewBranch, commit,
											innerProgress.newChild(1));
									break;
								default:
									break;
								}
							}
						};
						workspace.run(operation, null, IWorkspace.AVOID_UPDATE,
								progress.newChild(steps));
