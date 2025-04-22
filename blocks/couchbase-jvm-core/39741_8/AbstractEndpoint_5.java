    public RingBuffer<ResponseEvent> responseBuffer() {
        return responseBuffer;
    }

    protected static String logIdent(final Channel chan, final Endpoint endpoint) {
        SocketAddress addr = chan != null ? chan.remoteAddress() : null;
        return "["+addr+"][" + endpoint.getClass().getSimpleName()+"]: ";
    }
