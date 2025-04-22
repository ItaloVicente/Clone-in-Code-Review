      while(true) {
          final CountDownLatch latch = new CountDownLatch(100);
          for (int i = 0; i < 100; i++) {
              bucket().query(ViewQuery.from("foo", "bar").stale(Stale.TRUE)).subscribe(new Observer<ViewResult>() {
                  @Override
                  public void onCompleted() {
                      latch.countDown();
                  }

                  @Override
                  public void onError(Throwable e) {
                  }
