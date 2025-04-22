                                        AsyncAnalyticsQueryResult copyResult = new DefaultAsyncAnalyticsQueryResult(
                                                aqr.rows(),
                                                aqr.signature(),
                                                aqr.info(),
                                                cachedErrors,
                                                aqr.status(),
                                                aqr.parseSuccess(),
                                                aqr.requestId(),
                                                aqr.clientContextId()
                                        );
