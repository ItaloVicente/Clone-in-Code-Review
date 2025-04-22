								String taskName = NLS
										.bind(UIText.FetchGerritChangePage_FetchingTaskName,
												spec.getSource());
								monitor.setTaskName(taskName);
								fetchRes = new FetchOperationUI(repository,
										new URIish(uri), specs, timeout, false)
										.execute(monitor);

								monitor.worked(1);
								RevCommit commit = new RevWalk(repository)
										.parseCommit(fetchRes.getAdvertisedRef(
												spec.getSource()).getObjectId());
