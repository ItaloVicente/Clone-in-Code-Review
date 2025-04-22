            throws IllegalArgumentException, InterruptedException {
        boolean flag = false;
        for (int i = 0; i < 100; i++) {
            System.runFinalization();
            System.gc();
            Thread.yield();
            processEvents();
