                            throw SubdocHelper.commonSubdocErrors(response.status(), id, spec.path());
                        } finally {
                            if (environment.tracingEnabled()) {
                                environment.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
                            }
                        }
                    }
                }), request, environment, timeout, timeUnit);
