				};
				getContainer().run(false, true, monitor -> {
						if (monitor instanceof ProgressMonitorPart) {
							wizardProgressMonitor = (ProgressMonitorPart) monitor;
						}
					}
				);
