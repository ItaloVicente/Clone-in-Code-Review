            int queryLength = queryMsg.query() == null ? 0 : queryMsg.query().length();
            int keysLength = queryMsg.keys() == null ? 0 : queryMsg.keys().length();
            boolean hasQuery = queryLength > 0;
            boolean hasKeys = keysLength > 0;

            if (hasQuery || hasKeys) {
                if (queryLength + keysLength < MAX_GET_LENGTH) {
