  public void testReadOnliness() throws Exception {
    SocketAddress sa = new InetSocketAddress(11211);
    Mock m = mock(MemcachedNode.class, "node");
    MemcachedNodeROImpl node =
        new MemcachedNodeROImpl((MemcachedNode) m.proxy());
    m.expects(once()).method("getSocketAddress").will(returnValue(sa));

    assertSame(sa, node.getSocketAddress());
    assertEquals(m.proxy().toString(), node.toString());

    Set<String> acceptable = new HashSet<String>(Arrays.asList("toString",
        "getSocketAddress", "getBytesRemainingToWrite", "getReconnectCount",
        "getSelectionOps", "hasReadOp", "hasWriteOp", "isActive"));

    for (Method meth : MemcachedNode.class.getMethods()) {
      if (acceptable.contains(meth.getName())) {
        return;
      } else {
        Object[] args = new Object[meth.getParameterTypes().length];
        fillArgs(meth.getParameterTypes(), args);
        try {
          meth.invoke(node, args);
          fail("Failed to break on " + meth.getName());
        } catch (InvocationTargetException e) {
          assertSame("Fail at " + meth.getName(),
              UnsupportedOperationException.class, e.getCause().getClass());
        }
      }
    }
  }
