						CoreText.BranchOperation_performingBranch, target),
						missing.length > 0 ? 3 : 2);

				if (missing.length > 0) {
					SubProgressMonitor closeMonitor = new SubProgressMonitor(
							pm, 1);
					closeMonitor.beginTask("", missing.length); //$NON-NLS-1$
					for (IProject project : missing) {
						closeMonitor.subTask(MessageFormat.format(
								CoreText.BranchOperation_closingMissingProject,
								project.getName()));
						project.close(closeMonitor);
					}
					closeMonitor.done();
				}
