				};
				getContainer().run(false, true, monitor -> {
						if (monitor instanceof ProgressMonitorPart) {
							wizardProgressMonitor = (ProgressMonitorPart) monitor;
						}
					}
				);
				wizardProgressMonitor.attachToCancelComponent(null);
				wizardProgressMonitor.setVisible(true);
