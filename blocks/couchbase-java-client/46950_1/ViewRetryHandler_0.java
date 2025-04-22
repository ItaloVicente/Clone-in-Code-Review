                    ViewQueryResponse res = new ViewQueryResponse(
                        response.rows(),
                        response.info().cache(1),
                        response.responseCode(),
                        response.responsePhrase(),
                        response.status(),
                        response.request()
                    );
                    return passThroughOrThrow(res);
