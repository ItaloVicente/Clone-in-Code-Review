    @Test
    public void leakTest() throws InterruptedException {
        int loops = 3;
        Thread.sleep(600);
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        System.out.println("Initial Threads (will be ignored later):");
        Set<String> ignore = dump(threads(mx));
        int[] peaks = new int[loops];

        for (int i = 0; i < loops; i++) {
            System.out.println("\nPass " + (i+1));
            CoreEnvironment env = DefaultCoreEnvironment.create();
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());
            env.scheduler().createWorker().schedule(Actions.empty());

            System.out.println("===Created threads:");
            Set<String> afterCreate = dump(threads(mx, ignore));
            Thread.sleep(100);

            env.shutdown().toBlocking().last();
            Set<String> afterShutdown = threads(mx, ignore);

            peaks[i] = afterShutdown.size();
            System.out.println("===Shutdown went from " + afterCreate.size() + " to " + afterShutdown.size() + " threads, remaining: ");
            dump(afterShutdown);
        }
        System.out.print("\n========Thread peaks : " + peaks[0]);
        boolean peakGrowing = false;
        for (int i = 1; i < loops; i++) {
            System.out.print(" " + peaks[i]);
            peakGrowing = peakGrowing || (peaks[i] != peaks[i - 1]);
        }
        assertFalse("Number of threads is growing despite shutdown, see console output", peakGrowing);
    }

    private Set<String> dump(Set<String> threads) {
        for (String thread : threads) {
            System.out.println(thread);
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

