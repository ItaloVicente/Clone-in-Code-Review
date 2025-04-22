
                    Span span = environment.tracer()
                      .buildSpan("Decode")
                      .asChildOf(response.request().span())
                      .startActive(true).span();

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
                        span.finish();
                        response.request().span().finish();
                    }
