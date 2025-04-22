    if ((channel == null) || (!channel.isConnected())) {
        getLogger().warn("Channel is not connected for key " + sk
                + " on node " + qa);
        getLogger().warn("Client will retry the connection on node " + qa);
        return;
    }
