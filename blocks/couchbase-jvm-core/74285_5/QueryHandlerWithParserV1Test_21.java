    }

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        commonSetup();
        handler = new QueryHandler(endpoint, responseRingBuffer, queue, false, false, false);
