    OperationFuture<Boolean> deleteOp =
            (((CouchbaseClient)client).delete("observetest",
                PersistTo.MASTER));
    assert deleteOp.get()
            : "Key was not deleted on master : "
            + deleteOp.getStatus().getMessage();
