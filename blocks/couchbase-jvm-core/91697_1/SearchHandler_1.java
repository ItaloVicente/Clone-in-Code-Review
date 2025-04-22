    @Override
    protected void finishedDecoding() {
        releaseResponseContent();
        super.finishedDecoding();
    }

    private void releaseResponseContent() {
        if (responseContent != null && responseContent.refCnt() > 0) {
            responseContent.release();
        }
        responseContent = null;
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        releaseResponseContent();
        super.handlerRemoved(ctx);
    }

