      SocketChannel ch = SocketChannel.open();
      ch.configureBlocking(false);
      MemcachedNode qa =
          this.connectionFactory.createMemcachedNode(sa, ch, bufSize);
      int ops = 0;
      ch.socket().setTcpNoDelay(!this.connectionFactory.useNagleAlgorithm());
