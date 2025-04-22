    private void maybeLogRingBufferSizes() {
        if (ringBufferLogCounter++ % 10 == 0) {
            long usedReq = environment.requestBufferSize() - requestDisruptor.getRingBuffer().remainingCapacity();
            long usedRes = environment.responseBufferSize() - responseDisruptor.getRingBuffer().remainingCapacity();
            LOGGER.trace("RequestBuffer Entries {} used / {} total, ResponseBuffer Entries {} used / {} total", usedReq, environment.requestBufferSize(), usedRes, environment.responseBufferSize());
        }
    }

