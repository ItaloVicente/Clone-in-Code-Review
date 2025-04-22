    storeCallback = new StoreOperation.Callback() {
      public void complete() {
        fail("Unexpected invocation");
      }

      public void gotData(String key, long cas) {
      }
