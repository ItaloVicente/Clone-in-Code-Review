            @Override
            public boolean removeEldestEntry(Map.Entry eldest) {
                if (removeEldestEntryIterations > config.getJgitRemoveEldestEntryIterations()) {
                    removeEldestEntryIterations = 0;
                    return false;
                }
                if (size() > config.getJgitFileSystemsInstancesCache()) {
                    JGitFileSystem targetFS = (JGitFileSystem) ((MemoizedFileSystemsSupplier) eldest.getValue()).get();
                    if (targetFS.hasBeenInUse()) {
                        removeEldestEntryIterations++;
                        this.remove(eldest.getKey());
                        this.put((String) eldest.getKey(), (MemoizedFileSystemsSupplier) eldest.getValue());
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
    }
