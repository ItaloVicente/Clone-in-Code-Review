    /**
     * Helper method to conver the span set into the log output format.
     */
    private Map<String, Object> convertZombieSet(SortedSet<ThresholdLogSpan> set, String ident) {
        Map<String, Object> output = new HashMap<String, Object>();
        List<Map<String, Object>> top = new ArrayList<Map<String, Object>>();
        for (ThresholdLogSpan span : set) {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put(KEY_TOTAL_MICROS, span.durationMicros());

            String spanId = span.request().operationId();
            String operation_id = span.operationName() + (spanId == null ? "" : ":" + spanId);
            entry.put("operation_id", system(operation_id).toString());

            String local = span.request().lastLocalSocket();
            String peer = span.request().lastRemoteSocket();
            if (local != null) {
                entry.put("local_address", system(local).toString());
            }
            if (peer != null) {
                entry.put("remote_address", system(peer).toString());
            }

            String localId = span.request().lastLocalId();
            if (localId != null) {
                entry.put("local_id", system(localId).toString());
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

            top.add(entry);
        }
        output.put("service", ident);
        output.put("count", set.size());
        output.put("top", top);
        return output;
    }


