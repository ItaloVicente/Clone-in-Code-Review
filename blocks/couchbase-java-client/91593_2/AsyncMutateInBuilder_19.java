                                CouchbaseException subdocError = SubdocHelper.commonSubdocErrors(response.status(), docId, spec.path());
                                if (SubdocHelper.isSubdocLevelError(status)) {
                                    throw new MultiMutationException(0, status, Collections.singletonList(spec), subdocError);
                                } else {
                                    throw subdocError;
                                }
                            }
                        } finally {
                            if (environment.tracingEnabled()) {
                                environment.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
