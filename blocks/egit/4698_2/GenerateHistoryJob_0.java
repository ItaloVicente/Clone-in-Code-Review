					if (allCommits.size() != 1)
						monitor.setTaskName(MessageFormat
								.format(UIText.GenerateHistoryJob_taskFoundMultipleCommits,
										Integer.valueOf(allCommits.size())));
					else
						monitor.setTaskName(UIText.GenerateHistoryJob_taskFoundSingleCommit);

