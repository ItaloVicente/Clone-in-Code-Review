     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     * - The server is currently not able to process the request, retrying may help: {@link TemporaryFailureException}
     * - The server is out of memory: {@link CouchbaseOutOfMemoryException}
     * - Unexpected errors are caught and contained in a generic {@link CouchbaseException}.
