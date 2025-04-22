      new OperationFuture<Boolean>("", latch, operationTimeout) {
        private CouchbaseConnectionFactory factory =
          (CouchbaseConnectionFactory) connFactory;

        @Override
        public boolean cancel() {
          throw new UnsupportedOperationException("Flush cannot be"
            + " canceled");
        }
