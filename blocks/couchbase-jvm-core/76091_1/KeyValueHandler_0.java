        try {
            if (!(msg instanceof ObserveRequest)
                    && !(msg instanceof ObserveSeqnoRequest)
                    && (request instanceof FullBinaryMemcacheRequest)) {
                ((FullBinaryMemcacheRequest) request).content().retain();
            }
        } catch(IllegalReferenceCountException ex) {
            request.getExtras().release();
            throw ex;
