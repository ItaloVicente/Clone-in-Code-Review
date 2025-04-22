  private Operation handleReadsWhenChannelEndOfStream(final Operation currentOp,
    final MemcachedNode node, final ByteBuffer rbuf) throws IOException {
    if (currentOp instanceof TapOperation) {
      currentOp.getCallback().complete();
      ((TapOperation) currentOp).streamClosed(OperationState.COMPLETE);

      getLogger().debug("Completed read op: %s and giving the next %d bytes",
        currentOp, rbuf.remaining());
      Operation op = node.removeCurrentReadOp();
      assert op == currentOp : "Expected to pop " + currentOp + " got " + op;
      return node.getCurrentReadOp();
    } else {
      throw new IOException("Disconnected unexpected, will reconnect.");
    }
  }

