        return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri);
    }

    private void handleViewQueryResponse(ChannelHandlerContext ctx, HttpObject msg, List<Object> in) {
        switch (currentState) {
            case INITIAL:
                if (msg instanceof HttpResponse) {
                    HttpResponse response = (HttpResponse) msg;
                    currentState = ParsingState.PREAMBLE;
                    return;
                } else {
                    throw new IllegalStateException("Only expecting HttpResponse in INITIAL");
                }
            case PREAMBLE:
                if (msg instanceof HttpContent) {
                    HttpContent content = (HttpContent) msg;
                    if (content.content().readableBytes() > 0) {
                        currentChunk.writeBytes(content.content());
                        content.content().clear();
                    }

                    int pos = currentChunk.bytesBefore((byte) ',');
                    if (pos > 0) {
                        String[] rowsInfo = currentChunk.readBytes(pos+1).toString(CharsetUtil.UTF_8).split(":");
                        currentTotalRows = Integer.parseInt(rowsInfo[1].substring(0, rowsInfo[1].length()-1));
                    } else {
                        return;
                    }
                    if (currentChunk.readableBytes() >= 8) {
                        currentChunk.readerIndex(currentChunk.readerIndex() + 8);
                    } else {
                        return;
                    }
                } else {
                    throw new IllegalStateException("Only expecting HttpContent in PREAMBLE");
                }
                currentState = ParsingState.ROWS;
            case ROWS:
                if (msg instanceof HttpContent) {
                    HttpContent content = (HttpContent) msg;
                    if (content.content().readableBytes() > 0) {
                        currentChunk.writeBytes(content.content());
                        content.content().clear();
                    }
                    MarkerProcessor processor = new MarkerProcessor();
                    currentChunk.forEachByte(new MarkerProcessor());

                    boolean last = msg instanceof LastHttpContent;
                    ResponseStatus status = last ? ResponseStatus.SUCCESS : ResponseStatus.CHUNKED;
                    ByteBuf returnContent = currentChunk.readBytes(processor.marker());
                    if (processor.marker() > 0 || last) {
                        in.add(new ViewQueryResponse(status, currentTotalRows, returnContent.copy()));
                        currentChunk.discardSomeReadBytes();
                    }
                    returnContent.release();

                    if (last) {
                        currentRequest = null;
                        currentChunk.release();
                        currentChunk = null;
                        currentState = ParsingState.INITIAL;
                    }
                } else {
                    throw new IllegalStateException("Only expecting HttpContent in ROWS");
                }
        }
    }

    static enum ParsingState {
        INITIAL,

        PREAMBLE,

        ROWS
    }

    private static class MarkerProcessor implements ByteBufProcessor {
        private int marker = 0;
        private int depth;
        private byte open = '{';
        private byte close = '}';
        private int counter = 0;
        @Override
        public boolean process(byte value) throws Exception {
            counter++;
            if (value == open) {
                depth++;
            }
            if (value == close) {
                depth--;
                if (depth == 0) {
                    marker = counter;
                }
            }
            return true;
        }

        public int marker() {
            return marker;
        }
