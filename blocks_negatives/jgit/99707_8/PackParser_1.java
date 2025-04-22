				if (resolving instanceof BatchingProgressMonitor) {
					((BatchingProgressMonitor) resolving).setDelayStart(
							1000,
							TimeUnit.MILLISECONDS);
				}
				resolving.beginTask(JGitText.get().resolvingDeltas, deltaCount);
				resolveDeltas(resolving);
				if (entryCount < expectedObjectCount) {
					if (!isAllowThin()) {
						throw new IOException(MessageFormat.format(
								JGitText.get().packHasUnresolvedDeltas,
								Long.valueOf(expectedObjectCount - entryCount)));
					}

					resolveDeltasWithExternalBases(resolving);

					if (entryCount < expectedObjectCount) {
						throw new IOException(MessageFormat.format(
								JGitText.get().packHasUnresolvedDeltas,
								Long.valueOf(expectedObjectCount - entryCount)));
					}
				}
				resolving.endTask();
