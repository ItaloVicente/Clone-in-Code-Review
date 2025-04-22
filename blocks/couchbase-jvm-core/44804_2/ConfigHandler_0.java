    private void releaseResponseConten() {
        if (responseContent != null) {
            if (responseContent.refCnt() > 0) {
                responseContent.release();
            }
            responseContent = null;
        }
    }

    @Override
    protected void finishedDecoding() {
        super.finishedDecoding();
        releaseResponseConten();
    }

