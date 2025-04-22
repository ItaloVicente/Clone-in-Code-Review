
                    if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                        SubdocOperationResult<Lookup> single = SubdocOperationResult
                                .createResult(spec.path(), Lookup.GET, response.status(), null);
                        return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(), Collections.singletonList(single));
                    } else {
                        throw SubdocHelper.commonSubdocErrors(response.status(), id, spec.path());
