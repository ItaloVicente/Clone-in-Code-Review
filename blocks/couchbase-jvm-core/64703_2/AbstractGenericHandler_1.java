            CouchbaseRequest keepAlive = createKeepAliveRequest();
            if (keepAlive != null) {
                keepAlive.observable().subscribe(new KeepAliveResponseAction(ctx));
                onKeepAliveFired(ctx, keepAlive);

                Channel channel = ctx.channel();
                if (channel.isActive() && channel.isWritable()) {
                    ctx.pipeline().writeAndFlush(keepAlive);
