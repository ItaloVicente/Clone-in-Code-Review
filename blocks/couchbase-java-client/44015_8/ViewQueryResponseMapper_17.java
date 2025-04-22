    static class BuildSpatialViewResult implements Func1<JsonObject, AsyncSpatialViewResult> {

        private final AsyncBucket bucket;
        private final SpatialViewQuery query;
        private final ViewQueryResponse response;

        BuildSpatialViewResult(AsyncBucket bucket, SpatialViewQuery query, ViewQueryResponse response) {
            this.bucket = bucket;
            this.query = query;
            this.response = response;
        }

        @Override
        public AsyncSpatialViewResult call(JsonObject jsonInfo) {
            JsonObject error = null;
            JsonObject debug = null;
            boolean success = response.status().isSuccess();

            if (success) {
                debug = jsonInfo.getObject("debug_info");
            } else if (response.status() == ResponseStatus.NOT_EXISTS) {
                throw new ViewDoesNotExistException("View " + query.getDesign() + "/"
                    + query.getView() + " does not exist.");
            } else {
                error = jsonInfo;
            }

            Observable<AsyncSpatialViewRow> rows = response
                .rows()
                .map(new ByteBufToJsonObject())
                .map(new Func1<JsonObject, AsyncSpatialViewRow>() {
                    @Override
                    public AsyncSpatialViewRow call(JsonObject row) {
                        return new DefaultAsyncSpatialViewRow(bucket, row.getString("id"), row.getArray("key"),
                            row.get("value"), row.getObject("geometry"));
                    }
                });

            return new DefaultAsyncSpatialViewResult(rows, success, error, debug);
        }

    }

