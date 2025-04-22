    List<ConnectionObserver> initialObservers = new ArrayList<ConnectionObserver>();
    final CountDownLatch latch = new CountDownLatch(1);
    initialObservers.add(new ConnectionObserver() {
      @Override
      public void connectionEstablished(SocketAddress socketAddress, int i) {
        latch.countDown();
      }

      @Override
      public void connectionLost(SocketAddress socketAddress) {
      }
    });

