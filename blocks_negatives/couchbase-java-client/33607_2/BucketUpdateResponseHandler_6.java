  public void handleUpstream(ChannelHandlerContext context, ChannelEvent event)
    throws Exception {
    if (event instanceof ChannelStateEvent) {
      ChannelStateEvent csEvent = (ChannelStateEvent)event;
      LOGGER.log(Level.FINEST, "Channel state changed: {0}\n\n", csEvent);
      if (csEvent.getValue() == null
        LOGGER.log(Level.FINE, "Channel has been disconnected on us, "
          + "restarting the monitor.");
      } else if(csEvent.getState() == ChannelState.OPEN
        && !Boolean.valueOf(csEvent.getValue().toString())) {
        LOGGER.log(Level.FINE, "Channel has been closed on us, "
          + "restarting the monitor.");
      } else {
        LOGGER.log(Level.FINER, "Channel state change is not a disconnect. "
          + "Event value is {0} and Channel State is {1}.",
          new Object[]{csEvent.getValue().toString(),
            csEvent.getState().toString()});
      }
    }
    if (event.getChannel().isConnected()) {
      super.handleUpstream(context, event);
