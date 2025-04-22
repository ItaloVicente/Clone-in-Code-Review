
          final CountDownLatch latch = new CountDownLatch(1);
          final Map<SocketAddress, String> rv = new ConcurrentHashMap<SocketAddress, String>();
          final SocketAddress sa = qa.getSocketAddress();

          VersionOperation versionOp = opFact.version(new OperationCallback() {
            @Override
            public void receivedStatus(OperationStatus status) {
              rv.put(sa, status.getMessage());
            }

            @Override
            public void complete() {
              latch.countDown();
            }
          });

          qa.addOp(versionOp);
          handleReadsAndWrites(sk, qa);
          handleReadsAndWrites(sk, qa);

          boolean done = latch.await(connectionFactory.getOperationTimeout(), TimeUnit.MILLISECONDS);
          if (!done || versionOp.isCancelled() || versionOp.hasErrored() || versionOp.isTimedOut()) {
            throw new ConnectException("Could not send dummy version operation upon connect.");
          }

