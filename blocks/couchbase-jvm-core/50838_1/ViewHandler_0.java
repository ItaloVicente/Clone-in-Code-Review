
            int queryLength = queryMsg.query() == null ? 0 : queryMsg.query().length();
            int keysLength = queryMsg.keys() == null ? 0 : queryMsg.keys().length();
            boolean hasQuery = queryLength > 0;
            boolean hasKeys = keysLength > 0;

            if (hasQuery || hasKeys) {
                if (queryLength + keysLength < MAX_GET_LENGTH) {
                    if (hasQuery) {
                        path.append("?").append(queryMsg.query());
                        if (hasKeys) {
                            path.append("&keys=").append(encodeKeysGet(queryMsg.keys()));
                        }
                    } else {
                        path.append("?keys=").append(encodeKeysGet(queryMsg.keys()));
                    }
                } else {
                    if (hasQuery) {
                        path.append("?").append(queryMsg.query());
                    }
                    String keysContent = encodeKeysPost(queryMsg.keys());

                    method = HttpMethod.POST;
                    content = ctx.alloc().buffer(keysContent.length());
                    content.writeBytes(keysContent.getBytes(CHARSET));
                }
