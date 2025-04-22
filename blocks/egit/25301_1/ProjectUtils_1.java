
					if (!projectsToConnect.isEmpty()) {
						ConnectProviderOperation connect = new ConnectProviderOperation(
								projectsToConnect);
						connect.execute(new SubProgressMonitor(actMonitor, 1));
					}
