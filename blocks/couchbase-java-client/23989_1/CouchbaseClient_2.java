        @Override
        public Boolean get(long duration, TimeUnit units) throws
          InterruptedException, TimeoutException,
          ExecutionException {
          if (!latch.await(duration, units)) {
            throw new TimeoutException("Flush not completed within"
              + " timeout.");
          }
