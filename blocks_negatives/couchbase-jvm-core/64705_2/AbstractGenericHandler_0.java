            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.ALL_IDLE) {
                CouchbaseRequest keepAlive = createKeepAliveRequest();
                if (keepAlive != null) {
                    keepAlive.observable().subscribe(new KeepAliveResponseAction(ctx));
                    onKeepAliveFired(ctx, keepAlive);

                    Channel channel = ctx.channel();
                    if (channel.isActive() && channel.isWritable()) {
                        ctx.pipeline().writeAndFlush(keepAlive);
                    }
