				}

				if (!projectsToConnect.isEmpty()) {
					ConnectProviderOperation connect = new ConnectProviderOperation(
							projectsToConnect);
					connect.execute(progress.newChild(1));
