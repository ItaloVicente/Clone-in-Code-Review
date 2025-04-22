  TapCustomOperationImpl(String id, RequestMessage message,
      OperationCallback cb) {
    super(cb);
    this.id = id;
    this.message = message;
  }
