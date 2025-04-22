      public void receivedStatus(OperationStatus status) {
        rv.set(stats, status);
      }

      public void complete() {
        latch.countDown();
      }
    });
