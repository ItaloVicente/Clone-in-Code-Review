  @Test
  public void testQueryWithError() {
    CountDownLatch latch = new CountDownLatch(1);
    Collection<RowError> error = new LinkedList<RowError>();
    error.add(new RowError("a", "reason"));

    ViewFuture vf = new ViewFuture(latch, 2000, true);
    ViewResponse vr = new ViewResponseNoDocs(new LinkedList<ViewRow>(), error);
    vf.set(vr, new OperationStatus(true, "it worked"));
    try {
      vf.get();
    } catch (Exception e) {
      return;
    }
    assert false : ("An exception was not thrown for errors");
  }

