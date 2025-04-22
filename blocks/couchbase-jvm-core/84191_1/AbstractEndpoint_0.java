                        if (ss.isUnsubscribed()) {
                            if (cf.isSuccess() && cf.channel() != null) {
                                cf.channel().close().addListener(new ChannelFutureListener() {
                                    @Override
                                    public void operationComplete(ChannelFuture future) throws Exception {
                                        if (!future.isSuccess()) {
                                            LOGGER.debug("Got exception while disconnecting " +
                                                "stray connect attempt.", future.cause());
                                        }
                                    }
                                });
                            }
                        } else {
                            ss.onSuccess(cf);
                        }
