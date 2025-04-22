					} catch (IOException e) {
						listSize = loadedCommits.size();
						status = new Status(IStatus.ERROR,
								Activator.getPluginId(),
								UIText.GenerateHistoryJob_errorComputingHistory,
								e);
