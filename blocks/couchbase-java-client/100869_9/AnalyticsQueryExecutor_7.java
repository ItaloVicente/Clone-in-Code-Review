                                        AsyncAnalyticsQueryResult copyResult;
                                        if (aqr.handle() != null) {
                                            copyResult = new DefaultAsyncAnalyticsQueryResult(
                                                    aqr.handle(),
                                                    aqr.signature(),
                                                    aqr.info(),
                                                    cachedErrors,
                                                    aqr.status(),
                                                    aqr.parseSuccess(),
                                                    aqr.requestId(),
                                                    aqr.clientContextId()
                                            );
                                        } else {
                                            copyResult = new DefaultAsyncAnalyticsQueryResult(
                                                    aqr.rows(),
                                                    aqr.signature(),
                                                    aqr.info(),
                                                    cachedErrors,
                                                    aqr.status(),
                                                    aqr.parseSuccess(),
                                                    aqr.requestId(),
                                                    aqr.clientContextId()
                                            );
                                        }
