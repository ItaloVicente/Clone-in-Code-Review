					monitor.worked(20);
					if(monitor.isCanceled()) {
						return;
					}
					execute(op, MarkerMessages.markCompletedAction_title, new SubProgressMonitor(monitor, 80), null);
					monitor.done();
