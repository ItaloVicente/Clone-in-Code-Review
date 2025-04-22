  @Test
  public void testGetFromReplicaListener() throws Exception {
    if (client.getAvailableServers().size() < 2) {
      return;
    }

    assertTrue(client.set("asyncKey", "value", ReplicateTo.ONE).get());
    ReplicaGetFuture<Object> future = client.asyncGetFromReplica("asyncKey");

    final CountDownLatch latch = new CountDownLatch(1);
    future.addListener(new ReplicaGetCompletionListener() {
      @Override
      public void onComplete(ReplicaGetFuture<?> f) throws Exception {
        String found = (String) f.get();
        if(found.equals("value")) {
          latch.countDown();
        }
      }
    });

    assertTrue("Async latch was not counted down",
      latch.await(5, TimeUnit.SECONDS));
  }

