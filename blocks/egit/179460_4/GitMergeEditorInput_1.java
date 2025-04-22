				boolean useWorkingTree = !conflicting || useWorkspace;
				if (!useWorkingTree && conflicting && dirCacheEntry != null) {
					useWorkingTree = !Instant.EPOCH
							.equals(dirCacheEntry.getLastModifiedInstant());
				}
				if (useWorkingTree) {
