    @Test
    public void thresholdMaxTest() {
        long lastAccessThreshold = Long.MAX_VALUE;
        JGitFileSystemLock lock = createLock(lastAccessThreshold);
        lock.registerAccess();
        assertTrue(lock.hasBeenInUse());
    }
