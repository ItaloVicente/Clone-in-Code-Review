    void emit(CouchbaseResponse response);

    void complete();

    void succeed(CouchbaseResponse response);

    void fail(Throwable throwable);

