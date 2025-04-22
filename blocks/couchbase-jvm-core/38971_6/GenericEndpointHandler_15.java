    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            LOGGER.debug("Connection reset by peer", cause);
            rescheduleOutstandingOps();
        } else {
            LOGGER.warn("Caught unknown exception in handler", cause);
            ctx.fireExceptionCaught(cause);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        rescheduleOutstandingOps();
    }

    private void rescheduleOutstandingOps() {
        if (queue.isEmpty()) {
            return;
        }
        LOGGER.debug("Rescheduling " + queue.size() + " outstanding requests on " + endpoint.getClass().getSimpleName());
        while(!queue.isEmpty()) {
            CouchbaseRequest req = queue.poll();
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, req, req.observable());
        }
    }

