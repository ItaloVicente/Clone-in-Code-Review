    @Test
    public void shouldNotLeakThreadsWithDefaultConfiguration() throws InterruptedException {
        int loops = 3;
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        LOGGER.info("Initial Threads (will be ignored later):");
        Set<String> ignore = dump(threads(mx));
        int[] peaks = new int[loops];

        for (int i = 0; i < loops; i++) {
            CoreEnvironment env = DefaultCoreEnvironment.create();
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());

            LOGGER.info("===Created threads:");
            Set<String> afterCreate = dump(threads(mx, ignore));

            env.shutdown().toBlocking().last();
            Set<String> afterShutdown = threads(mx, ignore);

            peaks[i] = afterShutdown.size();
            LOGGER.info("===Shutdown went from " + afterCreate.size() + " to " + afterShutdown.size() + " threads, remaining: ");
            dump(afterShutdown);
        }
        boolean peakGrowing = false;
        StringBuilder peaksDump = new StringBuilder("========Thread peaks : ").append(peaks[0]);
        for (int i = 1; i < loops; i++) {
            peaksDump.append(' ').append(peaks[i]);
            peakGrowing = peakGrowing || (peaks[i] != peaks[i - 1]);
        }
        LOGGER.info(peaksDump.toString());
        assertFalse("Number of threads is growing despite shutdown, see console output", peakGrowing);
    }

    private Set<String> dump(Set<String> threads) {
        for (String thread : threads) {
            LOGGER.info(thread);
        }
        return threads;
    }

    private Set<String> threads(ThreadMXBean mx, Set<String> ignore) {
        Set<String> all = threads(mx);
        all.removeAll(ignore);
        return all;
    }

    private Set<String> threads(ThreadMXBean mx) {
        ThreadInfo[] dump = mx.getThreadInfo(mx.getAllThreadIds());
        Set<String> names = new HashSet<String>(dump.length);
        for (ThreadInfo threadInfo : dump) {
            names.add(threadInfo.getThreadName());
        }
        return names;
    }

    @Test
    public void shouldShowUnmanagedCustomResourcesInEnvDump() {
        DefaultCoreEnvironment env = DefaultCoreEnvironment.builder()
                .ioPool(new LocalEventLoopGroup())
                .scheduler(Schedulers.trampoline()).build();
        String dump = env.dumpParameters(new StringBuilder()).toString();

        assertTrue(dump, dump.contains("LocalEventLoopGroup!unmanaged"));
        assertTrue(dump, dump.contains("TrampolineScheduler!unmanaged"));
    }

    @Test
    public void shouldShowOnlyClassNameForManagedResourcesInEnvDump() {
        DefaultCoreEnvironment env = DefaultCoreEnvironment.create();
        String dump = env.dumpParameters(new StringBuilder()).toString();

        assertFalse(dump, dump.contains("!unmanaged"));
    }

