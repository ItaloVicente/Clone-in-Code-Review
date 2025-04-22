
                    ActiveSpan span = environment.tracer()
                      .buildSpan("Decode")
                      .asChildOf(response.request().span())
                      .startActive();

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
                        span.close();
                        response.request().finishSpan();
                    }
