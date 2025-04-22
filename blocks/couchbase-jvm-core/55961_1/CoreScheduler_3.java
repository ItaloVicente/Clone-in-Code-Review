            int c = size;
            if (c == 0) {
                return SHUTDOWN_WORKER;
            }
            return eventLoops[(int)(n++ % c)];
        }

        public void shutdown() {
            for (PoolWorker w : eventLoops) {
                w.unsubscribe();
            }
