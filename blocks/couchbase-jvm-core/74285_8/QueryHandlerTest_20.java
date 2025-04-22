    protected Queue<QueryRequest> queue;
    protected EmbeddedChannel channel;
    protected Disruptor<ResponseEvent> responseBuffer;
    protected RingBuffer<ResponseEvent> responseRingBuffer;
    protected List<CouchbaseMessage> firedEvents;
    protected CountDownLatch latch;
    protected AbstractEndpoint endpoint;
    protected ChannelHandler handler;

    protected void commonSetup() {
