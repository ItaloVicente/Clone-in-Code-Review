    assert setOp.get()
            : "Key set was not persisted to master : "
            + setOp.getStatus().getMessage();
    OperationFuture<Boolean> replaceOp =
            (((CouchbaseClient)client).replace("observetest", 0, "value",
                PersistTo.MASTER));
    assert replaceOp.get()
            : "Key replace was not persisted to master : "
            + replaceOp.getStatus().getMessage();
    OperationFuture<Boolean> deleteOp =
            (((CouchbaseClient)client).delete("observetest",
                PersistTo.MASTER));
    assert deleteOp.get()
            : "Key was not deleted on master : "
            + deleteOp.getStatus().getMessage();
    OperationFuture<Boolean> addOp =
            (((CouchbaseClient)client).add("observetest", 0, "value",
                PersistTo.MASTER, ReplicateTo.ZERO));
    assert addOp.get()
            : "Key add was not persisted to master : "
            + addOp.getStatus().getMessage();
