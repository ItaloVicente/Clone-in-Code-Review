    public void consumed(final DCPMessage event) {
        consumed(event.totalBodyLength());
    }

    public void consumed(final FullBinaryMemcacheResponse response) {
        consumed(response.getTotalBodyLength());
