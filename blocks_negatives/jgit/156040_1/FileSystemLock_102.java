    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemLock.class);

    ReentrantLock lock = new ReentrantLock(true);
    private FileLock physicalLock;
    private Path lockFile;
    private FileChannel fileChannel;
    private long lastAccessMilliseconds;
    private long lastAccessThresholdMilliseconds;
    private final URI repoURI;
    private String lockName;

    protected FileSystemLock(File directory,
                             String lockName,
                             TimeUnit lastAccessTimeUnit,
                             long lastAccessThreshold) {
        this.lockName = lockName;
        repoURI = directory.toURI();
        this.lockFile = createLockInfra(repoURI);
        this.lastAccessThresholdMilliseconds = lastAccessTimeUnit.toMillis(lastAccessThreshold);
    }

    void registerAccess() {
        lastAccessMilliseconds = System.currentTimeMillis();
    }

    public void lock() {
        registerAccess();
        lock.lock();

        if (needToCreatePhysicalLock()) {
            physicalLockOnFS();
        }
    }

    public void unlock() {
        registerAccess();
        if (lock.isLocked()) {
            if (releasePhysicalLock()) {
                physicalUnLockOnFS();
            }
            lock.unlock();
        }
    }

    public boolean hasBeenInUse() {
        if (recentlyAccessed()) {
            return true;
        }
        return lock.isLocked();
    }

    private boolean recentlyAccessed() {
        return (System.currentTimeMillis() - lastAccessMilliseconds) < lastAccessThresholdMilliseconds;
    }

    private boolean needToCreatePhysicalLock() {
        return ((physicalLock == null || !physicalLock.isValid()) && lock.getHoldCount() == 1);
    }

    private boolean releasePhysicalLock() {
        return physicalLock != null && physicalLock.isValid() && lock.isLocked() && lock.getHoldCount() == 1;
    }

    void physicalLockOnFS() {
        try {
            File file = lockFile.toFile();
            RandomAccessFile raf = new RandomAccessFile(file,
                                                        "rw");
            fileChannel = raf.getChannel();
            physicalLock = fileChannel.lock();
            fileChannel.position(0);
            fileChannel.write(ByteBuffer.wrap("locked".getBytes()));
        } catch (Exception e) {
            LOGGER.error("Error during lock of FS [" + repoURI.toString() + " -- " + this.getLockName() + "]",
                         e);
            throw new RuntimeException(e);
        }
    }

    void physicalUnLockOnFS() {
        try {
            physicalLock.release();
            fileChannel.close();
            fileChannel = null;
            physicalLock = null;
        } catch (Exception e) {
            LOGGER.error("Error during unlock of FS [" + repoURI.toString() + " -- " + this.getLockName() + "]",
                         e);
            throw new RuntimeException(e);
        }
    }

    Path createLockInfra(URI uri) {
        Path lockFile = null;
        try {
            Path repo = Paths.get(uri);
            lockFile = repo.resolve(getLockName());
            Files.createFile(lockFile);
        } catch (FileAlreadyExistsException ignored) {
        } catch (Exception e) {
            LOGGER.error("Error building lock infra [" + toString() + "]",
                         e);
            throw new RuntimeException(e);
        }
        return lockFile;
    }

    protected String getLockName() {
        return this.lockName;
    }
