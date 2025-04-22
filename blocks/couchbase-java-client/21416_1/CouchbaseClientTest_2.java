
    OperationFuture<Boolean> noPersistOp =
            (((CouchbaseClient)client).add("observetest", 0, "value",
              ReplicateTo.ONE));
    assert noPersistOp.get()
            : "Key add was not persisted to master : "
            + addOp.getStatus().getMessage();
