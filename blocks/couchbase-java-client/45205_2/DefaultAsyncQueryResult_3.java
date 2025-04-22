    public boolean parseSuccess() {
        return parsingSuccess;
    }

    @Override
    public Observable<JsonObject> errors() {
        return errors;
