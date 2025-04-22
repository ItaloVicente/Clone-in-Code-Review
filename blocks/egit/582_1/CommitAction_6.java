					try {
						workspace.run(operation, MultiRule
								.combine(filesToCommit),
								IWorkspace.AVOID_UPDATE, subMonitor.newChild(1,
										SubMonitor.SUPPRESS_NONE));
					} catch (CoreException e) {
						monitor.done();
						return e.getStatus();
