    /**
     * Size of the buffer to control speed of DCP producer.
     */
    @Deprecated
    int dcpConnectionBufferSize();

    /**
     * When a DCP connection read bytes reaches this percentage of the {@link #dcpConnectionBufferSize},
     * a DCP Buffer Acknowledge message is sent to the server
     */
    @Deprecated
    double dcpConnectionBufferAckThreshold();

