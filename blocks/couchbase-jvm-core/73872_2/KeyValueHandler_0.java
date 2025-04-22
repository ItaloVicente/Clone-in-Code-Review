        FullBinaryMemcacheRequest request;

        if (sslEnabled) {
            request = new DefaultFullBinaryMemcacheRequest(key, extras, msg.content().copy());
        } else {
            request = new DefaultFullBinaryMemcacheRequest(key, extras, msg.content());
        }
