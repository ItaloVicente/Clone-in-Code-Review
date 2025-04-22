
    OperationFuture<Boolean> noPersistOp =
            (((CouchbaseClient)client).add("nopersisttest", 0, "value",
              ReplicateTo.ONE));
    assert noPersistOp.get()
            : "Key add was not correctly replicated: "
            + addOp.getStatus().getMessage();
