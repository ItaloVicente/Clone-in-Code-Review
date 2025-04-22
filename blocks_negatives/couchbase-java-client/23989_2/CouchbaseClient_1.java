            new OperationFuture<Boolean>("", latch, operationTimeout) {

              CouchbaseConnectionFactory factory =
                (CouchbaseConnectionFactory) connFactory;

              @Override
              public boolean cancel() {
                throw new UnsupportedOperationException("Flush cannot be"
                  + " canceled");
              }

              @Override
              public boolean isDone() {
                return flushRunner.status();
              }
