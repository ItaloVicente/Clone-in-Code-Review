
                        if (currentRequest.span() != null) {
                            if (env.tracingEnabled()) {
                                env.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
                            }
                        }
