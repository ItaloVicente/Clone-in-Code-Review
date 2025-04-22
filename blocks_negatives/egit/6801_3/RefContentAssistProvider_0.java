				new ProgressMonitorDialog(shell).run(false, true,
						new IRunnableWithProgress() {

							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								monitor
										.beginTask(
												UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage,
												IProgressMonitor.UNKNOWN);
								lop.run(monitor);
								monitor.done();
							}
						});
				for (Ref ref : lop.getRemoteRefs())
					if (ref.getName().startsWith(Constants.R_HEADS)
							|| (!pushMode && ref.getName().startsWith(
									Constants.R_TAGS)))
						result.add(ref);
