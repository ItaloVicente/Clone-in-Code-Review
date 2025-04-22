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
