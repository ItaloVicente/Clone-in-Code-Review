					if (loadedCommits.size() != 1)
						monitor.setTaskName(MessageFormat
								.format(UIText.GenerateHistoryJob_taskFoundMultipleCommits,
										Integer.valueOf(loadedCommits.size())));
					else
						monitor.setTaskName(UIText.GenerateHistoryJob_taskFoundSingleCommit);
