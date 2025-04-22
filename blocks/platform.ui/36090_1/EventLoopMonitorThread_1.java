					if (threadId == uiThreadId) {
						if (index != 0) {
							thread = threadStacks[0];
							threadStacks[0] = threadStacks[i];
						}
					} else if (!isInteresting(thread)) {
						continue; // Skip the non-interesting thread.
