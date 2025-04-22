        long cas = msg.getCAS();
        if(current instanceof GetBucketConfigRequest) {
            InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
            in.add(
                new GetBucketConfigResponse(
                    convertStatus(msg.getStatus()),
                    bucket,
                    msg.content().copy(),
                    InetAddress.getByName(addr.getHostName())
                )
            );
