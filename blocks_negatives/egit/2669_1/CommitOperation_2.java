						if (!prepareTrees(filesToCommit, treeMap, actMonitor)) {
							for (Repository repo : treeMap.keySet())
								repo.getIndex().read();
							return;
						}
					} catch (IOException e) {
