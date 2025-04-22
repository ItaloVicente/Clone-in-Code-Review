        /**
         * Helper method which drains the queue, handles the sets and logs if needed.
         */
        private void handlerZombieQueue() {
            long now = System.nanoTime();
            if ((now - lastZombieLog + logIntervalNanos) > 0) {
                prepareAndLogZombies();
                lastZombieLog = now;
            }

            while (true) {
                ThresholdLogSpan span = zombieQueue.poll();
                if (span == null) {
                    return;
                }
                String service = (String) span.tag(Tags.PEER_SERVICE.getKey());
                if (SERVICE_KV.equals(service)) {
                    updateSet(kvZombieSet, span, true);
                } else if (SERVICE_N1QL.equals(service)) {
                    updateSet(n1qlZombieSet, span, true);
                } else if (SERVICE_VIEW.equals(service)) {
                    updateSet(viewZombieSet, span, true);
                } else if (SERVICE_FTS.equals(service)) {
                    updateSet(ftsZombieSet, span, true);
                } else if (SERVICE_ANALYTICS.equals(service)) {
                    updateSet(analyticsZombieSet, span, true);
                } else {
                    LOGGER.warn("Unknown service in span {}", service);
                }
            }
        }

