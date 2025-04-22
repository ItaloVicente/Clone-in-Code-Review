        } else if (msg instanceof RemoveBucketRequest) {
            httpMethod = HttpMethod.DELETE;
        }

        ByteBuf content;
        if (msg instanceof InsertBucketRequest) {
            content = Unpooled.copiedBuffer(((InsertBucketRequest) msg).payload(), CharsetUtil.UTF_8);
        } else if (msg instanceof UpdateBucketRequest) {
            content = Unpooled.copiedBuffer(((UpdateBucketRequest) msg).payload(), CharsetUtil.UTF_8);
