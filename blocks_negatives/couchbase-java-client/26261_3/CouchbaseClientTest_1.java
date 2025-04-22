  /**
   * Tests observe and persist to more nodes than configured.
   *
   * @pre Call set operations on the client object
   * with persist nodes more than the server size.
   * @post Asserts false.
   *
   * @throws Exception
   */
  public void testPersistToMoreThanConf() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    int size = client.getAvailableServers().size();
    OperationFuture<Boolean> future = null;
    switch(size){
    case 0:
      future = cb.set("something", 0,
        "to_store", PersistTo.ONE);
      assertFalse(future.get());
      break;
    case 1:
      future = cb.set("something", 0,
        "to_store", PersistTo.TWO);
      assertFalse(future.get());
      break;
    case 2:
      future = cb.set("something", 0,
        "to_store", PersistTo.THREE);
      assertFalse(future.get());
      break;
    case 3:
      future = cb.set("something", 0,
        "to_store", PersistTo.FOUR);
      assertFalse(future.get());
      break;
    default:
      break;
    }
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the persistence constraint.";
    assertEquals(expected, future.getStatus().getMessage());
  }

  /**
   * Tests observe and replicate to more nodes than configured.
   *
   * @pre Call set operations on the client object
   * with replicate nodes more than the server size.
   * @post Asserts false.
   *
   * @throws Exception
   */
  public void testReplicateToMoreThanConf() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    int size = client.getAvailableServers().size();
    OperationFuture<Boolean> future = null;
    switch(size){
    case 0:
      future = cb.set("something", 0,
        "to_store", ReplicateTo.ONE);
      assertFalse(future.get());
      break;
    case 1:
      future = cb.set("something", 0,
        "to_store", ReplicateTo.TWO);
      assertFalse(future.get());
      break;
    case 2:
      future = cb.set("something", 0,
        "to_store", ReplicateTo.THREE);
      assertFalse(future.get());
      break;
    default:
      break;
    }
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the replication constraint.";
    assertEquals(expected, future.getStatus().getMessage());
  }

