    requester.execute(
      new BasicAsyncRequestProducer(host, op.getRequest()),
      new BasicAsyncResponseConsumer(),
      pool,
      coreContext,
      new HttpResponseCallback(op, viewConnection)
    );
