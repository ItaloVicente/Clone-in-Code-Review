								if (Activator.getDefault().getPreferenceStore()
										.getBoolean(
												UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
									markStartAllRefs(currentWalk,
											Constants.R_HEADS);
									markStartAllRefs(currentWalk,
											Constants.R_REMOTES);
								} else {
									currentWalk
											.markStart(currentWalk.parseCommit(
													repository.resolve(
															Constants.HEAD)));
								}
