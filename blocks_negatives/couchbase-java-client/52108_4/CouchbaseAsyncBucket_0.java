                    if (foundStatus == ObserveResponse.ObserveStatus.FOUND_PERSISTED
                        || foundStatus == ObserveResponse.ObserveStatus.FOUND_NOT_PERSISTED) {
                        return true;
                    }

                    return false;
