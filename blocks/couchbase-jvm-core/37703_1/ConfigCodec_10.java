    protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
        if (currentRequest == null) {
            currentRequest = queue.poll();
        }

        if (currentRequest instanceof BucketConfigRequest) {
            handleBucketConfigResponse(msg, out);
        } else if (currentRequest instanceof BucketStreamingRequest) {
            handleBucketStreamingResponse(msg, out);
        }
    }

    private void handleBucketStreamingResponse(HttpObject msg, List<Object> out) {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            if (response.getStatus().code() == 200) {
                configStream = PublishSubject.create();
                out.add(new BucketStreamingResponse(configStream, ResponseStatus.SUCCESS, null));
            }
        }
        if (msg instanceof HttpContent) {
            currentConfig.append(((HttpContent) msg).content().toString(CharsetUtil.UTF_8));
            if (currentConfig.indexOf("\n\n\n\n") > 0) {
                configStream.onNext(currentConfig.toString().trim());
                currentConfig.setLength(0);
            }
        }
    }

    private void handleBucketConfigResponse(final HttpObject msg, final List<Object> out) {
        if (msg instanceof HttpContent) {
            currentConfig.append(((HttpContent) msg).content().toString(CharsetUtil.UTF_8));

            if (msg instanceof LastHttpContent) {
                out.add(new BucketConfigResponse(currentConfig.toString(), ResponseStatus.SUCCESS));
                currentConfig.setLength(0);
                currentRequest = null;
            }
