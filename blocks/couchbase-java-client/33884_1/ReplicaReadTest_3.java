  @Test
  public void testGetsFromReplica() throws Exception {
    if (client.getAvailableServers().size() < 2) {
      return;
    }

    assertTrue(client.set("getskey", 0, "foovalue", ReplicateTo.ONE).get());
    ReplicaGetFuture<CASValue<Object>> future = client.asyncGetsFromReplica("getskey");
    CASValue<Object> result = future.get();
    assertTrue(result.getCas() != 0);
    assertEquals(result.getValue(), "foovalue");
  }

  @Test
  public void testGetsFromReplicaListener() throws Exception {
    if (client.getAvailableServers().size() < 2) {
      return;
    }

    assertTrue(client.set("asyncKeyGets", "value", ReplicateTo.ONE).get());
    ReplicaGetFuture<CASValue<Object>> future =
      client.asyncGetsFromReplica("asyncKeyGets");

    final CountDownLatch latch = new CountDownLatch(1);
    future.addListener(new ReplicaGetCompletionListener() {
      @Override
      public void onComplete(ReplicaGetFuture<?> f) throws Exception {
        CASValue<Object> found = (CASValue<Object>) f.get();
        if(found.getValue().equals("value") && found.getCas() != 0) {
          latch.countDown();
        }
      }
    });

    assertTrue("Async latch was not counted down",
      latch.await(5, TimeUnit.SECONDS));
  }

