  @Test
  public void testGetFromReplicaWithNonExistentKey() {
    assertEquals(null, client.getFromReplica("nonexistentKey"));
  }

  @Test
  public void testGetsFromReplicaWithNonExistentKey() {
      assertEquals(null, client.getsFromReplica("nonexistentKey"));
  }

