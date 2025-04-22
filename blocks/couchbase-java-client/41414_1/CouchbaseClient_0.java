    @Override
    public HttpFuture<N1qlResponse> asyncN1Query(String n1Query) {
        final CountDownLatch latch = new CountDownLatch(1);
        final N1qlFuture future = new N1qlFuture(latch, connFactory.getOperationTimeout(), executorService);
        final HttpRequest request = new BasicHttpRequest("POST", "/query", HttpVersion.HTTP_1_1);
        final HttpOperation op =  new N1qlOperation(request, /* callback */); //TODO: Implement N1ql Operation and call back.

        future.setOperation(op);
        addOp(op);
        return future;
    }


    @Override
  public N1qlResponse n1Query(String n1Query) {
        try {
            return asyncN1Query(n1Query).get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while reading query response.");
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to execute N1 query.");
        }
    }

    @Override
