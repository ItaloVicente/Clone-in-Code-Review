          } catch (IOException e) {
            getLogger().warn("Exception handling write", e);
            lostConnection(qa);
          }
        }
        qa.fixupOps();
      }
      addedQueue.addAll(toAdd);
    }
  }

  public boolean addObserver(ConnectionObserver obs) {
    return connObservers.add(obs);
  }

  public boolean removeObserver(ConnectionObserver obs) {
    return connObservers.remove(obs);
  }

  private void connected(MemcachedNode qa) {
    assert qa.getChannel().isConnected() : "Not connected.";
    int rt = qa.getReconnectCount();
    qa.connected();
    for (ConnectionObserver observer : connObservers) {
      observer.connectionEstablished(qa.getSocketAddress(), rt);
    }
  }

  private void lostConnection(MemcachedNode qa) {
    queueReconnect(qa);
    for (ConnectionObserver observer : connObservers) {
      observer.connectionLost(qa.getSocketAddress());
    }
  }

  private void handleIO(SelectionKey sk) {
    MemcachedNode qa = (MemcachedNode) sk.attachment();
    try {
      getLogger().debug("Handling IO for:  %s (r=%s, w=%s, c=%s, op=%s)", sk,
              sk.isReadable(), sk.isWritable(), sk.isConnectable(),
              sk.attachment());
      if (sk.isConnectable()) {
        getLogger().info("Connection state changed for %s", sk);
        final SocketChannel channel = qa.getChannel();
        if (channel.finishConnect()) {
          connected(qa);
          addedQueue.offer(qa);
          if (qa.getWbuf().hasRemaining()) {
            handleWrites(sk, qa);
          }
        } else {
          assert !channel.isConnected() : "connected";
        }
      } else {
        if (sk.isValid() && sk.isReadable()) {
          handleReads(sk, qa);
        }
        if (sk.isValid() && sk.isWritable()) {
          handleWrites(sk, qa);
        }
      }
    } catch (ClosedChannelException e) {
      if (!shutDown) {
        getLogger().info("Closed channel and not shutting down. Queueing"
            + " reconnect on %s", qa, e);
        lostConnection(qa);
      }
    } catch (ConnectException e) {
      getLogger().info("Reconnecting due to failure to connect to %s", qa, e);
      queueReconnect(qa);
    } catch (OperationException e) {
      qa.setupForAuth(); // noop if !shouldAuth
      getLogger().info("Reconnection due to exception handling a memcached "
          + "operation on %s. This may be due to an authentication failure.",
          qa, e);
      lostConnection(qa);
    } catch (Exception e) {
      qa.setupForAuth(); // noop if !shouldAuth
      getLogger().info("Reconnecting due to exception on %s", qa, e);
      lostConnection(qa);
    }
    qa.fixupOps();
  }

  private void handleWrites(SelectionKey sk, MemcachedNode qa)
    throws IOException {
    qa.fillWriteBuffer(shouldOptimize);
    boolean canWriteMore = qa.getBytesRemainingToWrite() > 0;
    while (canWriteMore) {
      int wrote = qa.writeSome();
      qa.fillWriteBuffer(shouldOptimize);
      canWriteMore = wrote > 0 && qa.getBytesRemainingToWrite() > 0;
    }
  }

  private void handleReads(SelectionKey sk, MemcachedNode qa)
    throws IOException {
    Operation currentOp = qa.getCurrentReadOp();
    if (currentOp instanceof TapAckOperationImpl) {
      qa.removeCurrentReadOp();
      return;
    }
    ByteBuffer rbuf = qa.getRbuf();
    final SocketChannel channel = qa.getChannel();
    int read = channel.read(rbuf);
    if (read < 0) {
      if (currentOp instanceof TapOperation) {
        currentOp.getCallback().complete();
        ((TapOperation) currentOp).streamClosed(OperationState.COMPLETE);
        getLogger().debug("Completed read op: %s and giving the next %d bytes",
            currentOp, rbuf.remaining());
        Operation op = qa.removeCurrentReadOp();
        assert op == currentOp : "Expected to pop " + currentOp + " got " + op;
        queueReconnect(qa);
        currentOp = qa.getCurrentReadOp();
      } else {
        throw new IOException("Disconnected unexpected, will reconnect.");
      }
    }
    while (read > 0) {
      getLogger().debug("Read %d bytes", read);
      rbuf.flip();
      while (rbuf.remaining() > 0) {
        if (currentOp == null) {
          throw new IllegalStateException("No read operation.");
