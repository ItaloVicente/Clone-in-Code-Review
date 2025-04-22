      ChannelStateEvent csEvent = (ChannelStateEvent)event;
      LOGGER.log(Level.FINEST, "Channel state changed: {0}\n\n", csEvent);
      if (csEvent.getValue() == null
              && csEvent.getState() == ChannelState.CONNECTED) { // a disconnect
        LOGGER.log(Level.FINE, "Channel has been disconnected on us, "
          + "restarting the monitor.");
        monitor.notifyDisconnected(); // connection has been dropped
      } else {
        LOGGER.log(Level.FINER, "Channel state change is not a disconnect. "
          + "Event value is {0} and Channel State is {1}.",
          new Object[]{csEvent.getValue().toString(),
            csEvent.getState().toString()});
      }
    }
    if (event.getChannel().isConnected()) {
      super.handleUpstream(context, event);
