    if ((channel == null) || (!channel.isConnected())) {
        getLogger().warn("Channel is not connected for key " + sk
                + " on node \"" + qa + "\".  Client will attempt to"
          + " reconnect.");
        return;
    }
