    private Queue<QueryRequest> queue;
    private EmbeddedChannel channel;
    private Disruptor<ResponseEvent> responseBuffer;
    private RingBuffer<ResponseEvent> responseRingBuffer;
    private List<CouchbaseMessage> firedEvents;
    private CountDownLatch latch;
    private QueryHandler handler;
    private AbstractEndpoint endpoint;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
