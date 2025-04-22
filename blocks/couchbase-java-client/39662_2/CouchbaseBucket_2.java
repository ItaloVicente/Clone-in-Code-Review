
    @Override
    public Observable<Boolean> removeDesignDocument(final String name) {
        return Observable
            .from(true, false)
            .flatMap(new Func1<Boolean, Observable<? extends Boolean>>() {
                @Override
                public Observable<? extends Boolean> call(Boolean development) {
                    return removeDesignDocument(name, development);
                }
            }).last();
    }

    @Override
    public Observable<Boolean> removeDesignDocument(String name, boolean development) {
            .<RemoveDesignDocumentResponse>send(new RemoveDesignDocumentRequest(name, development))
            .map(new Func1<RemoveDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(RemoveDesignDocumentResponse response) {
                    return true;
                }
            });*/
        return null;
    }

    @Override
    public Observable<DesignDocument> replaceDesignDocument(final DesignDocument designDocument) {
        ReplaceDesignDocumentRequest req = new ReplaceDesignDocumentRequest(designDocument.name(),
            designDocument.development(), designDocument.toJson());
        return core
            .<ReplaceDesignDocumentResponse>send(req)
            .map(new Func1<ReplaceDesignDocumentResponse, DesignDocument>() {
                @Override
                public DesignDocument call(ReplaceDesignDocumentResponse response) {
                    return designDocument;
                }
            });
    }

    @Override
    public Observable<DesignDocument> insertDesignDocument(final DesignDocument designDocument) {
        InsertDesignDocumentRequest req = new InsertDesignDocumentRequest(designDocument.name(),
            designDocument.development(), designDocument.toJson());
        return core
            .<InsertDesignDocumentResponse>send(req)
            .map(new Func1<InsertDesignDocumentResponse, DesignDocument>() {
                @Override
                public DesignDocument call(InsertDesignDocumentResponse response) {
                    return designDocument;
                }
            });
    }

    @Override
    public Observable<DesignDocument> listDesignDocuments() {
        return core
            .<ListDesignDocumentResponse>send(new ListDesignDocumentsRequest(bucket, password))
            .flatMap(new Func1<ListDesignDocumentResponse, Observable<Tuple3<String, Boolean, String>>>() {
                @Override
                public Observable<Tuple3<String, Boolean, String>> call(ListDesignDocumentResponse response) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map<String, Object> parsed = mapper.readValue(response.content(),
                            new TypeReference<HashMap<String, Object>>() {
                            }
                        );

                        List<Map<String, Object>> rows = (List<Map<String, Object>>) parsed.get("rows");
                        List<Tuple3<String, Boolean, String>> docs = new ArrayList<Tuple3<String, Boolean, String>>();
                        for (Map<String, Object> row : rows) {
                            String ddoc = mapper.writeValueAsString(((Map) row.get("doc")).get("json"));
                            String name = (String) ((Map) ((Map) row.get("doc")).get("meta")).get("id");
                            name = name.replace("_design/", "");
                            boolean development = false;
                            if (name.contains("dev_")) {
                                name = name.replace("dev_", "");
                                development = true;
                            }
                            docs.add(Tuple.create(name, development, ddoc));
                        }
                        return Observable.from(docs);
                    } catch (IOException e) {
                        return Observable.error(new CouchbaseException("Could not parse design doc response", e));
                    }
                }
            }).map(new Func1<Tuple3<String, Boolean, String>, DesignDocument>() {
                @Override
                public DesignDocument call(Tuple3<String, Boolean, String> response) {
                    return DesignDocument.from(response.value1(), response.value2(), response.value3());
                }
            });
    }

    @Override
    public Observable<DesignDocument> getDesignDocument(final String name) {
        return Observable
            .from(true, false)
            .flatMap(new Func1<Boolean, Observable<? extends DesignDocument>>() {
                @Override
                public Observable<? extends DesignDocument> call(final Boolean development) {
                    return getDesignDocument(name, development);
                }
            });
    }

    @Override
    public Observable<DesignDocument> getDesignDocument(final String name, final boolean development) {
        return core
            .<GetDesignDocumentResponse>send(new GetDesignDocumentRequest(name, development, bucket, password))
            .filter(new Func1<GetDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(final GetDesignDocumentResponse response) {
                    return response.status().isSuccess();
                }
            })
            .map(new Func1<GetDesignDocumentResponse, DesignDocument>() {
                @Override
                public DesignDocument call(final GetDesignDocumentResponse response) {
                    String content = response.content().toString(CharsetUtil.UTF_8);
                    return DesignDocument.from(response.name(), response.development(), content);
                }
            });
    }

