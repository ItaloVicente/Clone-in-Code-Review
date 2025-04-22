                if (BINARY_ERROR_MAP == null) {
                    LOGGER.warn("Unexpected ResponseStatus with Protocol KeyValue: {} (0x{}, {})",
                            status, Integer.toHexString(status.code()), status.description());
                    return ResponseStatus.FAILURE;
                } else {
                    return ResponseStatus.FAILURE;
                }
