  @Test
  public void testV2FetchServerNegotiationUsingWaitForDoneWhenServerHasFewCommits()
      throws Exception {
    String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

    RevBlob parentBlob = remote.blob(commonInBlob + "a");
    RevCommit parent = remote.commit(remote.tree(remote.file("foo"
    remote.update("branch1"

    RevCommit localChild = null;
    try (TestRepository<InMemoryRepository> local = new TestRepository<>(client)) {
      RevBlob localParentBlob = local.blob(commonInBlob + "a");
      RevCommit localParent = local.commit(local.tree(local.file("foo"
      RevBlob localChildBlob = local.blob(commonInBlob + "b");
      localChild = local.commit(local.tree(local.file("foo"
      local.update("branch1"
    }

    ByteArrayInputStream recvStream =
        uploadPackV2(
            "command=fetch\n"
            PacketLineIn.delimiter()
            "wait-for-done\n"
            "have " + parent.toObjectId().getName() + "\n"
            "have " + localChild.toObjectId().getName() + "\n"
            PacketLineIn.end());
    PacketLineIn pckIn = new PacketLineIn(recvStream);
    assertThat(pckIn.readString()
    assertThat(Arrays.asList(pckIn.readString())
    assertTrue(PacketLineIn.isEnd(pckIn.readString()));
  }

  @Test
  public void testV2FetchServerNegotiationUsingWaitForDoneWhenServerHasNoCommits()
      throws Exception {
    String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

    RevCommit localParent = null;
    RevCommit localChild = null;
    try (TestRepository<InMemoryRepository> local = new TestRepository<>(client)) {
      RevBlob localParentBlob = local.blob(commonInBlob + "a");
      localParent = local.commit(local.tree(local.file("foo"
      RevBlob localChildBlob = local.blob(commonInBlob + "b");
      localChild = local.commit(local.tree(local.file("foo"
      local.update("branch1"
    }

    ByteArrayInputStream recvStream =
        uploadPackV2(
            "command=fetch\n"
            PacketLineIn.delimiter()
            "wait-for-done\n"
            "have " + localParent.toObjectId().getName() + "\n"
            "have " + localChild.toObjectId().getName() + "\n"
            PacketLineIn.end());
    PacketLineIn pckIn = new PacketLineIn(recvStream);
    assertThat(pckIn.readString()
    assertThat(pckIn.readString()
    assertTrue(PacketLineIn.isEnd(pckIn.readString()));
  }

