
      HttpHost httpHost;
      while (true) {
        httpHost = viewNodes.get(getNextNode());
        if (hasActiveVBuckets(httpHost)) {
          break;
        }
      }

      requester.execute(
        new BasicAsyncRequestProducer(httpHost, op.getRequest()),
        new BasicAsyncResponseConsumer(),
        pool,
        coreContext,
        new HttpResponseCallback(op, this)
      );
