  @Before
  public void setUp() {

  }

  @Test
  public void testGetPipeline() throws Exception {
    BucketMonitorPipelineFactory factory = new BucketMonitorPipelineFactory();
    ChannelPipeline pipeline = factory.getPipeline();

    ChannelHandler decoder = pipeline.get("decoder");
    ChannelHandler encoder = pipeline.get("encoder");
    ChannelHandler handler = pipeline.get("handler");
    assertTrue(decoder instanceof HttpResponseDecoder);
    assertTrue(encoder instanceof HttpRequestEncoder);
    assertTrue(handler instanceof BucketUpdateResponseHandler);
    assertEquals(handler, pipeline.getLast());
    assertEquals(decoder, pipeline.getFirst());
  }
