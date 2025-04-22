            ByteBuf content = msg.content().copy();
            if (msg.getDataType() == 2 || msg.getDataType() == 3) {
                ByteBuf compressed = ctx.alloc().buffer();
                snappy.decode(content, compressed);
                content.release();
                content = compressed;
            }
            in.add(new GetResponse(status, cas, bucket, content, currentRequest));
