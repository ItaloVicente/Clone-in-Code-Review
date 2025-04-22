        try {
            if (!(msg instanceof ObserveRequest)
                    && !(msg instanceof ObserveSeqnoRequest)
                    && (request instanceof FullBinaryMemcacheRequest)) {
                ((FullBinaryMemcacheRequest) request).content().retain();
            }
        } catch (IllegalReferenceCountException ex) {
            if (request.getExtras() != null && request.getExtras().refCnt() > 0) {
                try {
                    request.getExtras().release();
                } catch (Exception e) {
                }
            }
            throw ex;
