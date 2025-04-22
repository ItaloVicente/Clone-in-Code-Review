  private void handleEmptySelects() {
    getLogger().debug("No selectors ready, interrupted: "
      + Thread.interrupted());

    if (++emptySelects > DOUBLE_CHECK_EMPTY) {
      for (SelectionKey sk : selector.keys()) {
        getLogger().debug("%s has %s, interested in %s", sk, sk.readyOps(),
          sk.interestOps());
        if (sk.readyOps() != 0) {
          getLogger().debug("%s has a ready op, handling IO", sk);
          handleIO(sk);
        } else {
          lostConnection((MemcachedNode) sk.attachment());
        }
      }
      assert emptySelects < EXCESSIVE_EMPTY : "Too many empty selects";
    }
  }

