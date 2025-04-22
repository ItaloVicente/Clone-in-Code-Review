    return new ViewResponseWithDocs(rows, view.getErrors());
  }

  public void set(ViewResponse viewResponse,
      BulkFuture<Map<String, Object>> oper, OperationStatus s) {
    objRef.set(viewResponse);
    multigetRef.set(oper);
    status = s;
