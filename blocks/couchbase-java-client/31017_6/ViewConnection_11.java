  public void addOp(final HttpOperation op) {
    if (!running) {
      throw new IllegalStateException("Shutting down");
    }

    HttpCoreContext coreContext = HttpCoreContext.create();

    if (viewNodes.isEmpty()) {
      getLogger().error("No server connections. Cancelling op.");
      op.cancel();
    } else {
      if (!"default".equals(user)) {
        try {
          op.addAuthHeader(HttpUtil.buildAuthHeader(user, password));
        } catch (UnsupportedEncodingException ex) {
          getLogger().error("Could not create auth header for request, "
            + "could not encode credentials into base64. Canceling op."
            + op, ex);
          op.cancel();
          return;
        }
      }

      HttpHost httpHost = viewNodes.get(getNextNode());

      requester.execute(
        new BasicAsyncRequestProducer(httpHost, op.getRequest()),
        new BasicAsyncResponseConsumer(),
        pool,
        coreContext,
        new HttpResponseCallback(op, this, httpHost)
      );
    }
