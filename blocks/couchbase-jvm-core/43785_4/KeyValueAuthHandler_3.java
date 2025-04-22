            ChannelFuture future = ctx.writeAndFlush(stepRequest);
            future.addListener(new GenericFutureListener<Future<Void>>() {
                @Override
                public void operationComplete(Future<Void> future) throws Exception {
                    if (!future.isSuccess()) {
                        LOGGER.warn("Error during SASL Auth negotiation phase.", future);
                    }
                }
            });
