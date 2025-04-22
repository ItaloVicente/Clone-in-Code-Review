
    if (runTime > 0) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(runTime, timeunit));
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          conn.shutdown();
          synchronized (omap) {
            omap.remove(op);
          }
        }
      };
      new Thread(r).start();
    }
