  private void handleReadsAndWrites(final SelectionKey sk,
    final MemcachedNode node) throws IOException {
    if (sk.isValid()) {
      if (sk.isReadable()) {
        handleReads(node);
      }
      if (sk.isWritable()) {
        handleWrites(node);
      }
    }
  }

