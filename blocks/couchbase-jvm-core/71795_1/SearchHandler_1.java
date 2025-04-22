    public SearchHandler(AbstractEndpoint endpoint, EventSink<ResponseEvent> responseBuffer, boolean isTransient,
                         final boolean pipeline) {
        super(endpoint, responseBuffer, isTransient, pipeline);
    }

    SearchHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<SearchRequest> queue,
                boolean isTransient, final boolean pipeline) {
        super(endpoint, responseBuffer, queue, isTransient, pipeline);
