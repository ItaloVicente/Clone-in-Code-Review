    private RingBuffer<ResponseEvent> responseBuffer;

    @Before
    public void setup() {
        Executor executor = Executors.newFixedThreadPool(1);
        Disruptor<ResponseEvent> responseDisruptor = new Disruptor<ResponseEvent>(
            new ResponseEventFactory(),
            1024,
            executor
        );
        responseDisruptor.handleEventsWith(new ResponseHandler());
        responseDisruptor.start();
        responseBuffer = responseDisruptor.getRingBuffer();
    }

