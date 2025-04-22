					try {
						operation.execute(monitor);
					} catch (CoreException e) {
						return Activator.createErrorStatus(
								e.getStatus().getMessage(), e);
					}
					if (operation.isGitignoreOutsideWSChanged()) {
						refresh();
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					return Status.OK_STATUS;
				} finally {
					monitor.done();
