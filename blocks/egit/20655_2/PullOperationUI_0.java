
				for (Entry<Repository, Object> entry : res.entrySet()) {
					Object result = entry.getValue();
					if (result instanceof PullResult) {
						PullResult pullResult = (PullResult) result;
						if (pullResult.getRebaseResult() != null
								&& RebaseResult.Status.UNCOMMITTED_CHANGES == pullResult
										.getRebaseResult().getStatus()) {
							boolean handledSeparately = handleUncommittedChanges(
									entry.getKey(), pullResult
											.getRebaseResult()
											.getUncommittedChanges(), shell);
							if (handledSeparately)
								tasksToWaitFor.incrementAndGet();
						}
					}
				}

				if (tasksToWaitFor.decrementAndGet() == 0 && !results.isEmpty())
					showResults(shell);
