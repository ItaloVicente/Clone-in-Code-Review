        return fileSystemsLocks.computeIfAbsent(directory.getAbsolutePath(),
                                                key -> new FileSystemLock(directory,
                                                                          lockName,
                                                                          lastAccessTimeUnit,
                                                                          lastAccessThreshold));
    }
