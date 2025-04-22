                entry.put(KEY_TOTAL_MICROS, span.durationMicros());

                String spanId = (String) span.tag("couchbase.operation_id");
                String operation_id = span.operationName() + (spanId == null ? "" : ":" + spanId);
                entry.put("operation_id", operation_id);

                String local = span.getBaggageItem("local.address");
                String peer = span.getBaggageItem("peer.address");
                if (local != null) {
                    entry.put("local_address", local);
                }
                if (peer != null) {
                    entry.put("remote_address", peer);
                }

                String decode_duration = span.getBaggageItem(KEY_DECODE_MICROS);
                if (decode_duration != null) {
                    entry.put(KEY_DECODE_MICROS, Long.parseLong(decode_duration));
                }

                String encode_duration = span.getBaggageItem(KEY_ENCODE_MICROS);
                if (encode_duration != null) {
                    entry.put(KEY_ENCODE_MICROS, Long.parseLong(encode_duration));
                }

                String dispatch_duration = span.getBaggageItem(KEY_DISPATCH_MICROS);
                if (dispatch_duration != null) {
                    entry.put(KEY_DISPATCH_MICROS, Long.parseLong(dispatch_duration));
                }

                String server_duration = span.getBaggageItem(KEY_SERVER_MICROS);
                if (server_duration != null) {
                    entry.put(KEY_SERVER_MICROS, Long.parseLong(server_duration));
                }
