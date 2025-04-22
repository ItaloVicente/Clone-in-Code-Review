							ISchedulingRule rule = p.getWorkspace().getRuleFactory().refreshRule(p);
							try {
								getJobManager().beginRule(rule, subMonitor);
								if (p.isAccessible()) {
									p.refreshLocal(IResource.DEPTH_INFINITE,
											subMonitor.newChild(1));
								}
							} catch (CoreException e) {
								handleError(UIText.Activator_refreshFailed, e, false);
							} finally {
								getJobManager().endRule(rule);
