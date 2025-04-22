        @Override
        public Boolean get() throws InterruptedException,
          ExecutionException {
          try {
            return get(factory.getViewTimeout(), TimeUnit.MILLISECONDS);
          } catch (TimeoutException e) {
            throw new RuntimeException("Timed out waiting for operation",
              e);
          }
        }
