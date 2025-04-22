    private void handleListDesignDocumentResponse(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) {
        if (msg instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) msg;
            currentStatus = res.getStatus().code();
        }

        if (msg instanceof HttpContent) {
            currentConfig.append(((HttpContent) msg).content().toString(CharsetUtil.UTF_8));

            if (msg instanceof LastHttpContent) {
                switch(currentStatus) {
                    case 200:
                        out.add(new ListDesignDocumentResponse(currentConfig.toString(), ResponseStatus.SUCCESS, null));
                        break;
                    case 401:
                        out.add(new ListDesignDocumentResponse("Unauthorized", ResponseStatus.FAILURE, null));
                        break;
                    case 404:
                        out.add(new ListDesignDocumentResponse(currentConfig.toString(), ResponseStatus.NOT_EXISTS, null));
                        break;
                }

                currentConfig.setLength(0);
                currentRequest = null;
            }
        }
    }

