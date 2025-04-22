            case ERROR:
                if (msg instanceof HttpContent) {
                    HttpContent content = (HttpContent) msg;
                    if (content.content().readableBytes() > 0) {
                        currentChunk.writeBytes(content.content());
                        content.content().clear();
                    }

                    if (msg instanceof LastHttpContent) {
                        ResponseStatus status = currentCode == 404 ? ResponseStatus.NOT_EXISTS : ResponseStatus.FAILURE;
                        in.add(new ViewQueryResponse(status, currentTotalRows, currentChunk.copy(), null));
                        reset();

                    }
                    return;
                }
