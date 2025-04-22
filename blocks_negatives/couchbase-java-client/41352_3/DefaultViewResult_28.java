    public DefaultViewResult(Observable<ViewRow> rows, int totalRows, boolean success, JsonObject error, JsonObject debug) {
        this.rows = rows;
        this.totalRows = totalRows;
        this.success = success;
        this.error = error;
        this.debug = debug;
