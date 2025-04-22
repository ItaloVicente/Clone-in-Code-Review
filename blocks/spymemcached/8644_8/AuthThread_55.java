      try {
        latch.await();
        Thread.sleep(100);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        if (op != null) {
          op.cancel();
        }
        done.set(true); // If we were interrupted, tear down.
      }
