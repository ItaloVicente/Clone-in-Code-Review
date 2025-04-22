			for (String conflict : conflicts) {
				int entryIdx = dc.findEntry(conflict);
				if (entryIdx >= 0) {
					while (entryIdx < dc.getEntryCount()) {
						DirCacheEntry entry = dc.getEntry(entryIdx);
						if (!entry.getPathString().equals(conflict)) {
							break;
						}
						if (entry.getStage() == DirCacheEntry.STAGE_3) {
							checkoutEntry(repo
									null);
							break;
						}
						++entryIdx;
					}
				}

				monitor.update(1);
				if (monitor.isCancelled()) {
					throw new CanceledException(MessageFormat.format(
							JGitText.get().operationCanceled
							JGitText.get().checkingOutFiles));
				}
			}
