    /**
     * A filter for the responses.
     *
     * This filter checks the response status and releases resources as needed.
     */
    private static class GetResponseFilter implements Func1<GetResponse, Boolean> {

        public static final GetResponseFilter INSTANCE = new GetResponseFilter();

        @Override
        public Boolean call(GetResponse response) {
            if (response.status().isSuccess()) {
                return true;
            }
            ByteBuf content = response.content();
            if (content != null && content.refCnt() > 0) {
                content.release();
            }

            switch (response.status()) {
                case NOT_EXISTS:
                    return false;
                case TEMPORARY_FAILURE:
                case SERVER_BUSY:
                    throw new TemporaryFailureException();
                case OUT_OF_MEMORY:
                    throw new CouchbaseOutOfMemoryException();
                default:
                    throw new CouchbaseException(response.status().toString());
            }
        }
    }

