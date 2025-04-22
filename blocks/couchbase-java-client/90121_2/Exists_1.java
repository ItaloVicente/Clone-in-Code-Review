
                        ObserveResponse.ObserveStatus foundStatus = response.observeStatus();
                        return foundStatus == ObserveResponse.ObserveStatus.FOUND_PERSISTED
                            || foundStatus == ObserveResponse.ObserveStatus.FOUND_NOT_PERSISTED;
                    }
                }), request, environment, timeout, timeUnit);
