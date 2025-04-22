					try {
						ThreadInfo[] rawThreadStacks = dumpAllThreads
							? jvmThreadManager.dumpAllThreads(dumpLockedMonitors, dumpLockedSynchronizers)
									: new ThreadInfo[] {
									jvmThreadManager.getThreadInfo(uiThreadId, Integer.MAX_VALUE)
							};

						ThreadInfo[] threadStacks = rawThreadStacks;
						if (dumpAllThreads) {
							int index = 0;
							threadStacks = new ThreadInfo[rawThreadStacks.length - 1];

							for (int i = 0; i < rawThreadStacks.length; i++) {
								ThreadInfo currentThread = rawThreadStacks[i];

								if (!isCurrentThread(currentThread.getThreadId())) {
									if (currentThread.getThreadId() == uiThreadId && i > 0) {
										currentThread = threadStacks[0];
										threadStacks[0] = rawThreadStacks[i];
									}
									threadStacks[index++] = currentThread;
								}
							}
						}

						stackSamples[numSamples++] = new StackSample(getTimestamp(), threadStacks);
						grabStackSampleAt += pollingDelay;
					} catch (SWTException e) {
						cancelled.set(true);
						resetStalledEventState = true;
