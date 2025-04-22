			private void fitListToCacheSize() {
				List<String> itemsToRemove = new ArrayList<>();
				int maxIterations = config.getJgitCacheOverflowCleanupSize();
				Object[] entries = this.entrySet().toArray();
				for (int i = this.size() - 1; (i >= 0 && (this.size() - i < maxIterations)); i--) {
					Map.Entry<String
					JGitFileSystem targetFS = (JGitFileSystem) ((MemoizedFileSystemsSupplier) entry.getValue()).get();
					if (!targetFS.hasBeenInUse()) {
						itemsToRemove.add(entry.getKey());
					}
				}
				itemsToRemove.forEach(item -> this.remove(item));
			}
