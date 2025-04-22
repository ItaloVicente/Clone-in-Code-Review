    ChannelHandler decoder = pipeline.get("decoder");
    ChannelHandler encoder = pipeline.get("encoder");
    ChannelHandler handler = pipeline.get("handler");
    assertTrue(decoder instanceof HttpResponseDecoder);
    assertTrue(encoder instanceof HttpRequestEncoder);
    assertTrue(handler instanceof BucketUpdateResponseHandler);
    assertEquals(handler, pipeline.getLast());
    assertEquals(decoder, pipeline.getFirst());
  }
