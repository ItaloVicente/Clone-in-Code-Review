								try {

									if (Activator
											.getDefault()
											.getPreferenceStore()
											.getBoolean(
													UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
										markStartAllRefs(currentWalk,
												Constants.R_HEADS);
										markStartAllRefs(currentWalk,
												Constants.R_REMOTES);
									} else
										currentWalk
												.markStart(currentWalk
														.parseCommit(repository
																.resolve(Constants.HEAD)));
									for (;;) {
										final int oldsz = allCommits.size();
										allCommits.fillTo(oldsz + BATCH_SIZE
												- 1);

										if (monitor.isCanceled()
												|| oldsz == allCommits.size())
											break;
										String taskName = MessageFormat.format(
														UIText.CommitSelectionDialog_FoundCommitsMessage,
														Integer
																.valueOf(allCommits
																		.size()));
										monitor.setTaskName(taskName);
