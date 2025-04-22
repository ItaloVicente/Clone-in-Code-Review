    List<QueryRow> allRows();

    List<QueryRow> allRows(long timeout, TimeUnit timeUnit);

    Iterator<QueryRow> rows();

    Iterator<QueryRow> rows(long timeout, TimeUnit timeUnit);

    JsonObject info();
