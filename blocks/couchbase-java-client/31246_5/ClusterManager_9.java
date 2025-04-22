
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean success = new AtomicBoolean(false);
        final AtomicReference<HttpResponse> response =
          new AtomicReference<HttpResponse>();

        requester.execute(
          new BasicAsyncRequestProducer(node, request),
          new BasicAsyncResponseConsumer(),
          pool,
          coreContext,
          new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse result) {
              latch.countDown();
              success.set(true);
              response.set(result);
            }

            @Override
            public void failed(Exception ex) {
              getLogger().debug("Cluster Response failed with: ", ex);
              latch.countDown();
            }

            @Override
            public void cancelled() {
              getLogger().debug("Cluster Response was cancelled.");
              latch.countDown();
            }
          }
        );

        latch.await();
        if (!success.get()) {
          getLogger().debug("Could not finish request execution");
