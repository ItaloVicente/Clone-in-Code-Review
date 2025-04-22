                        if (environment.operationTracingEnabled()) {
                            environment.tracer().scopeManager()
                              .activate(response.request().span(), true)
                              .close();
                        }

