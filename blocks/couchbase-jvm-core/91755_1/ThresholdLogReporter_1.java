                if (spanId != null) {
                    entry.put("operation_id", spanId);
                }

                String operationName = span.operationName();
                if (operationName != null) {
                    entry.put("operation_name", operationName);
                }
