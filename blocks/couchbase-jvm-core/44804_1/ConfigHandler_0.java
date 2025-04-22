    @Override
    protected void finishedDecoding() {
        super.finishedDecoding();
        if (responseContent != null) {
            if (responseContent.refCnt() > 0) {
                responseContent.release();
            }
            responseContent = null;
        }
    }

