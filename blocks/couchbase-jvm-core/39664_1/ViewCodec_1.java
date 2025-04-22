    private HttpRequest handleGetDesignDocumentRequest(final GetDesignDocumentRequest msg) {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("/").append(msg.bucket()).append("/_design/");
        requestBuilder.append(msg.development() ? "dev_" + msg.name() : msg.name());
        return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, requestBuilder.toString());
    }

    private void handleGetDesignDocumentResponse(HttpObject msg, List<Object> in) {
        if (msg instanceof HttpResponse) {
            currentResponse = (HttpResponse) msg;
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            if (content.content().readableBytes() > 0) {
                currentChunk.writeBytes(content.content());
                content.content().clear();
            }

            if (msg instanceof LastHttpContent) {

                String name = ((GetDesignDocumentRequest) currentRequest).name();
                boolean development = ((GetDesignDocumentRequest) currentRequest).development();

                ResponseStatus status;
                switch (currentResponse.getStatus().code()) {
                    case 200:
                        status = ResponseStatus.SUCCESS;
                        break;
                    case 404:
                        status = ResponseStatus.NOT_EXISTS;
                        break;
                    default:
                        status = ResponseStatus.FAILURE;
                }

                in.add(new GetDesignDocumentResponse(name, development, currentChunk.copy(), status, currentRequest));
                reset();
            }

        }
    }

