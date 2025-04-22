    private Logger logger = LoggerFactory.getLogger(JGitFileSystemProviderConcurrentOperationsTest.class);

    @Test
    public void testConcurrentGitCreation() {

        int threadCount = 2;
        final CountDownLatch finished = new CountDownLatch(threadCount);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int name = i;
            Runnable r = () -> {
                this.provider.createNewGitRepo(EMPTY_ENV,
                finished.countDown();
                logger.info("Countdown" + Thread.currentThread().getName());
            };
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }

        wait(threads);
        assertEquals(0,
                     finished.getCount());
    }

    @Test
    public void testConcurrentGitDeletion() throws IOException {

        final URI newRepo = URI.create(gitRepo);
        JGitFileSystemProxy fs = (JGitFileSystemProxy) provider.newFileSystem(newRepo,
                                                                              EMPTY_ENV);

        int threadCount = 2;
        final CountDownLatch finished = new CountDownLatch(threadCount);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int name = i;
            Runnable r = () -> {
                try {
                    this.provider.deleteFS(fs.getRealJGitFileSystem());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                finished.countDown();
                logger.info("Countdown" + Thread.currentThread().getName());
            };
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }

        wait(threads);
        assertEquals(0,
                     finished.getCount());
    }

    private void wait(List<Thread> threads) {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("Error waiting for threads",
                             e);
            }
        });
    }
