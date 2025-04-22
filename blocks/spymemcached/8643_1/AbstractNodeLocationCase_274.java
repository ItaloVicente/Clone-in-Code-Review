  public final void testCloningGetSequence() {
    setupNodes(5);
    assertTrue(locator.getReadonlyCopy().getSequence("hi").next()
        
        instanceof MemcachedNodeROImpl);
  }
