    HttpFuture<String> asyncHttpPut = c.asyncHttpPut(docUri, view);
    String response = asyncHttpPut.get();
    OperationStatus status = asyncHttpPut.getStatus();
    System.err.println("Operation Status is: " + status);
    if (!status.isSuccess()) {
      System.err.println("Operation Status is: " + status);
      assert false : "Could not load views: " + status.getMessage()
              + " with response " + response;
    }
