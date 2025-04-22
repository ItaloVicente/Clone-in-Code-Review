        Disruptor<ResponseEvent> responseDisruptor = new Disruptor<ResponseEvent>(
            new ResponseEventFactory(),
            environment.responseBufferSize(),
            executor
        );
        responseDisruptor.handleEventsWith(new ResponseHandler());
        responseDisruptor.start();
        responseRingBuffer = responseDisruptor.getRingBuffer();

        Disruptor<RequestEvent> requestDisruptor = new Disruptor<RequestEvent>(
