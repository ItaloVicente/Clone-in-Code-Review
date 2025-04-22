                if (BINARY_ERROR_MAP == null) {
                    LOGGER.warn("Unexpected ResponseStatus with Protocol KeyValue: {} (0x{}, {})",
                            status, Integer.toHexString(status.code()), status.description());
                    return ResponseStatus.FAILURE;
                } else {
                    ErrorMap.ErrorCode result = BINARY_ERROR_MAP.errors().get(status.code());
                    if (result == null) {
                        LOGGER.warn("Unexpected ResponseStatus with Protocol KeyValue and not found in " +
                            "Error Map: {} (0x{}, {})",  status, Integer.toHexString(status.code()),
                            status.description());
                    } else {
                        LOGGER.warn("Unexpected ResponseStatus with Extended Error {}", result.toString());
                    }
                    return ResponseStatus.FAILURE;
                }
