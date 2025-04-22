                .rows()
                .map(new ByteBufToJsonObject())
                .flatMap(new Func1<JsonObject, Observable<AsyncViewRow>>() {
                    @Override
                    public Observable<AsyncViewRow> call(final JsonObject row) {
                        final String id = row.getString("id");
