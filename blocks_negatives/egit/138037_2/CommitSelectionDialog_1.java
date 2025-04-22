							try {
								monitor
										.beginTask(
												UIText.CommitSelectionDialog_BuildingCommitListMessage,
												IProgressMonitor.UNKNOWN);
								SWTWalk currentWalk = new SWTWalk(repository);
