        return Observable.defer(new Func0<Observable<GetDesignDocumentsResponse>>() {
            @Override
            public Observable<GetDesignDocumentsResponse> call() {
                return core.send(new GetDesignDocumentsRequest(bucket, password));
            }
        }).flatMap(new Func1<GetDesignDocumentsResponse, Observable<DesignDocument>>() {
            @Override
            public Observable<DesignDocument> call(GetDesignDocumentsResponse response) {
                JsonObject converted;
                try {
                    converted = CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(response.content());
                } catch (Exception e) {
                    throw new TranscodingException("Could not decode design document.", e);
                }
                JsonArray rows = converted.getArray("rows");
                List<DesignDocument> docs = new ArrayList<DesignDocument>();
                for (Object doc : rows) {
                    JsonObject docObj = ((JsonObject) doc).getObject("doc");
                    String id = docObj.getObject("meta").getString("id");
                    String[] idSplit = id.split("/");
                    String fullName = idSplit[1];
                    boolean isDev = fullName.startsWith("dev_");
                    if (isDev != development) {
                        continue;
