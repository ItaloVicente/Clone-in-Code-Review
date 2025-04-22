					try {
						new ConnectProviderOperation(prj,
								myRepository.getDirectory()).execute(null);
					} catch (Exception e) {
						Activator.logError(
								"Failed to connect project to repository", e);
					}
