                switch(response.status()) {
                    case SUBDOC_MULTI_PATH_FAILURE:
                    int index = response.firstErrorIndex();
                    ResponseStatus errorStatus = response.firstErrorStatus();
                    String errorPath = mutationSpecs.get(index).path();
                    CouchbaseException errorException = SubdocHelper.commonSubdocErrors(errorStatus, docId, errorPath);

                    return Observable
                            .error(new MultiMutationException(index, errorStatus, mutationSpecs, errorException));
                    default:
                    return Observable.error(SubdocHelper.commonSubdocErrors(response.status(), docId, "MULTI-MUTATION"));
                }
