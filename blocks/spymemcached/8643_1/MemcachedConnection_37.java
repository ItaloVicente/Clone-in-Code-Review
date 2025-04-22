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
      getLogger()
          .debug("Handling IO for:  %s (r=%s, w=%s, c=%s, op=%s)", sk,
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
        getLogger().info(
            "Closed channel and not shutting down.  "
                + "Queueing reconnect on %s", qa, e);
        lostConnection(qa);
      }
    } catch (ConnectException e) {
      getLogger().info("Reconnecting due to failure to connect to %s", qa, e);
      queueReconnect(qa);
    } catch (OperationException e) {
      qa.setupForAuth(); // noop if !shouldAuth
      getLogger().info(
          "Reconnection due to exception "
              + "handling a memcached operation on %s.  "
              + "This may be due to an authentication failure.", qa, e);
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
        }
        currentOp.readFromBuffer(rbuf);
        if (currentOp.getState() == OperationState.COMPLETE) {
          getLogger().debug(
              "Completed read op: %s and giving the next %d bytes", currentOp,
              rbuf.remaining());
          Operation op = qa.removeCurrentReadOp();
          assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;
          currentOp = qa.getCurrentReadOp();
        } else if (currentOp.getState() == OperationState.RETRY) {
          getLogger().debug(
              "Reschedule read op due to NOT_MY_VBUCKET error: %s ", currentOp);
          ((VBucketAware) currentOp).addNotMyVbucketNode(currentOp
              .getHandlingNode());
          Operation op = qa.removeCurrentReadOp();
          assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;
          retryOps.add(currentOp);
          currentOp = qa.getCurrentReadOp();

        }
      }
      rbuf.clear();
      read = channel.read(rbuf);
    }
  }

  static String dbgBuffer(ByteBuffer b, int size) {
    StringBuilder sb = new StringBuilder();
    byte[] bytes = b.array();
    for (int i = 0; i < size; i++) {
      char ch = (char) bytes[i];
      if (Character.isWhitespace(ch) || Character.isLetterOrDigit(ch)) {
        sb.append(ch);
      } else {
        sb.append("\\x");
        sb.append(Integer.toHexString(bytes[i] & 0xff));
      }
    }
    return sb.toString();
  }

  private void queueReconnect(MemcachedNode qa) {
    if (!shutDown) {
      getLogger().warn("Closing, and reopening %s, attempt %d.", qa,
          qa.getReconnectCount());
      if (qa.getSk() != null) {
        qa.getSk().cancel();
        assert !qa.getSk().isValid() : "Cancelled selection key is valid";
      }
      qa.reconnecting();
      try {
        if (qa.getChannel() != null && qa.getChannel().socket() != null) {
          qa.getChannel().socket().close();
        } else {
          getLogger().info("The channel or socket was null for %s", qa);
        }
      } catch (IOException e) {
        getLogger().warn("IOException trying to close a socket", e);
      }
      qa.setChannel(null);

      long delay =
          (long) Math.min(maxDelay, Math.pow(2, qa.getReconnectCount())) * 1000;
      long reconTime = System.currentTimeMillis() + delay;

      while (reconnectQueue.containsKey(reconTime)) {
        reconTime++;
      }

      reconnectQueue.put(reconTime, qa);

      qa.setupResend();

      if (failureMode == FailureMode.Redistribute) {
        redistributeOperations(qa.destroyInputQueue());
      } else if (failureMode == FailureMode.Cancel) {
        cancelOperations(qa.destroyInputQueue());
      }
    }
  }

  private void cancelOperations(Collection<Operation> ops) {
    for (Operation op : ops) {
      op.cancel();
    }
  }

  private void redistributeOperations(Collection<Operation> ops) {
    for (Operation op : ops) {
      if (op instanceof KeyedOperation) {
        KeyedOperation ko = (KeyedOperation) op;
        int added = 0;
        for (String k : ko.getKeys()) {
          for (Operation newop : opFact.clone(ko)) {
            addOperation(k, newop);
            added++;
          }
        }
        assert added > 0 : "Didn't add any new operations when redistributing";
      } else {
        op.cancel();
      }
    }
  }

  private void attemptReconnects() throws IOException {
    final long now = System.currentTimeMillis();
    final Map<MemcachedNode, Boolean> seen =
        new IdentityHashMap<MemcachedNode, Boolean>();
    final List<MemcachedNode> rereQueue = new ArrayList<MemcachedNode>();
    SocketChannel ch = null;
    for (Iterator<MemcachedNode> i =
        reconnectQueue.headMap(now).values().iterator(); i.hasNext();) {
      final MemcachedNode qa = i.next();
      i.remove();
      try {
        if (!seen.containsKey(qa)) {
          seen.put(qa, Boolean.TRUE);
          getLogger().info("Reconnecting %s", qa);
          ch = SocketChannel.open();
          ch.configureBlocking(false);
          int ops = 0;
          if (ch.connect(qa.getSocketAddress())) {
            getLogger().info("Immediately reconnected to %s", qa);
            assert ch.isConnected();
          } else {
            ops = SelectionKey.OP_CONNECT;
          }
          qa.registerChannel(ch, ch.register(selector, ops, qa));
          assert qa.getChannel() == ch : "Channel was lost.";
        } else {
          getLogger().debug("Skipping duplicate reconnect request for %s", qa);
        }
      } catch (SocketException e) {
        getLogger().warn("Error on reconnect", e);
        rereQueue.add(qa);
      } catch (Exception e) {
        getLogger().error("Exception on reconnect, lost node %s", qa, e);
      } finally {
        if (ch != null && !ch.isConnected() && !ch.isConnectionPending()) {
          try {
            ch.close();
          } catch (IOException x) {
            getLogger().error("Exception closing channel: %s", qa, x);
          }
        }
      }
    }
    for (MemcachedNode n : rereQueue) {
      queueReconnect(n);
    }
  }

  NodeLocator getLocator() {
    return locator;
  }

  public void addOperation(final String key, final Operation o) {
    MemcachedNode placeIn = null;
    MemcachedNode primary = locator.getPrimary(key);
    if (primary.isActive() || failureMode == FailureMode.Retry) {
      placeIn = primary;
    } else if (failureMode == FailureMode.Cancel) {
      o.cancel();
    } else {
      for (Iterator<MemcachedNode> i = locator.getSequence(key); placeIn == null
          && i.hasNext();) {
        MemcachedNode n = i.next();
        if (n.isActive()) {
          placeIn = n;
        }
      }
      if (placeIn == null) {
        placeIn = primary;
        this.getLogger().warn(
            "Could not redistribute "
                + "to another node, retrying primary node for %s.", key);
      }
    }

    assert o.isCancelled() || placeIn != null : "No node found for key " + key;
    if (placeIn != null) {
      if (locator instanceof VBucketNodeLocator) {
        VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
        short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
        if (o instanceof VBucketAware) {
          VBucketAware vbucketAwareOp = (VBucketAware) o;
          vbucketAwareOp.setVBucket(key, vbucketIndex);
          if (!vbucketAwareOp.getNotMyVbucketNodes().isEmpty()) {
            MemcachedNode alternative =
                vbucketLocator.getAlternative(key,
                    vbucketAwareOp.getNotMyVbucketNodes());
            if (alternative != null) {
              placeIn = alternative;
