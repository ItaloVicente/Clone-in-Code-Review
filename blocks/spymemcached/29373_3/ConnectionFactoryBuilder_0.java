      @Override
      public ExecutorService getListenerExecutorService() {
        return executorService == null ? super.getListenerExecutorService() : executorService;
      }

      @Override
      public boolean isDefaultExecutorService() {
        return executorService == null;
      }
