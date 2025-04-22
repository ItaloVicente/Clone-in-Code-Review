        } else if (currentRequest instanceof FlushRequest) {
            handleFlushResponse(ctx, msg, out);
        }
    }


    private void handleFlushResponse(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) {
        if (msg instanceof HttpResponse) {
            int code = ((HttpResponse) msg).getStatus().code();
            ResponseStatus status = code == 200 ? ResponseStatus.SUCCESS : ResponseStatus.FAILURE;
            boolean done = code != 201;
            out.add(new FlushResponse(done, status));
            currentRequest = null;
