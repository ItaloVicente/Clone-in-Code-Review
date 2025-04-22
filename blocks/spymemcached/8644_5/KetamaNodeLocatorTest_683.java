    for (String[] s : exp) {
      String k = s[0];
      String server = s[1];
      MemcachedNode n = locator.getPrimary(k);
      assertEquals("/" + server, n.getSocketAddress().toString());
    }
