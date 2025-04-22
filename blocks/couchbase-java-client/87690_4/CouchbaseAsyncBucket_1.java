
                    Scope decodeScope = environment.tracer()
                      .buildSpan("ResponseDecoding")
                      .asChildOf(response.request().span())
                      .startActive(true);

                    try {
                        D decoded = (D) transcoder.decode(
                          id,
                          response.content(),
                          response.cas(),
                          0,
                          response.flags(),
                          response.status()
                        );
                        return decoded;
                    } finally {
                        decodeScope.close();
                        environment().tracer().scopeManager().activate(
                          response.request().span(), true
                        ).close();
                    }
