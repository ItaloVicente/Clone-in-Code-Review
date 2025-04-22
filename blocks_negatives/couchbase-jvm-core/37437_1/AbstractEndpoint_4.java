                   /*channel.write(request).addListener(new GenericFutureListener<Future<Void>>() {
                    @Override
                    public void operationComplete(Future<Void> future) throws Exception {
                        if (!future.isSuccess()) {
                            request.observable().onError(future.cause());
                        }
                    }
                });*/
