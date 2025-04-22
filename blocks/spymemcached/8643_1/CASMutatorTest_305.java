  public void testConcurrentCAS() throws Throwable {
    int num = SyncThread.getDistinctResultCount(20, new Callable<Long>() {
      public Long call() throws Exception {
        return mutator.cas("test.cas.concurrent", 0L, 0, mutation);
      }
    });
    assertEquals(20, num);
  }
