                    public Observable<E> call(List<JsonArrayDocument> jsonArrayDocuments) {
                        if (jsonArrayDocuments.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        JsonArrayDocument jsonArrayDocument = jsonArrayDocuments.get(0);
                        int size = jsonArrayDocument.content().size();
                        final Object val;
                        if (size > 0) {
                            val = jsonArrayDocument.content().get(size - 1);
                        } else {
                            return Observable.just(null);
                        }
                        if (mutationOptionBuilder.cas() != 0 && jsonArrayDocument.cas() != mutationOptionBuilder.cas()) {
