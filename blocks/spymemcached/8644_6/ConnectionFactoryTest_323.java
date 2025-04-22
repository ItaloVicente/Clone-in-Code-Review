  public void testQueueSizes() {
    ConnectionFactory cf = new DefaultConnectionFactory(100, 1024);
    assertEquals(100, cf.createOperationQueue().remainingCapacity());
    assertEquals(Integer.MAX_VALUE, cf.createWriteOperationQueue()
        .remainingCapacity());
    assertEquals(Integer.MAX_VALUE, cf.createReadOperationQueue()
        .remainingCapacity());
  }
