				};
				getContainer().run(false, true, monitor -> {
						if (monitor instanceof ProgressMonitorPart) {
							wizardProgressMonitor = (ProgressMonitorPart) monitor;
						}
					}
				);
				wizardProgressMonitor.attachToCancelComponent(null);
				wizardProgressMonitor.setVisible(true);
				ToolItem stopButton = getStopButton(wizardProgressMonitor);
				jobMonitor = ProgressManager.getInstance().progressFor(refreshProposalsJob);
