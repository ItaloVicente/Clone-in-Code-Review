    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<ObserveResponse> rv =
        new OperationFuture<ObserveResponse>(key, latch, operationTimeout);
    final AtomicReference<ObserveResponse> observeResult =
        new AtomicReference<ObserveResponse>(null);
    final ConcurrentLinkedQueue<Operation> ops =
        new ConcurrentLinkedQueue<Operation>();
