				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				monitor.setTaskName(MessageFormat.format(
						UIText.GenerateHistoryJob_taskFoundCommits,
						Integer.valueOf(listSize)));
				if (listSize > itemToLoad + (BATCH_SIZE / 2) + 1
						&& loadIncrementally) {
					break;
				}
				if (maxCommits > 0 && listSize > maxCommits) {
					incomplete = true;
					break;
				}
