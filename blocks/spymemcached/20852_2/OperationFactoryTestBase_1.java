    };
      deleteCallback = new DeleteOperation.Callback() {
        public void complete() {
          fail("Unexpected invocation");
        }

        public void gotData(long cas) {
        }

        public void receivedStatus(OperationStatus status) {
          fail("Unexpected status:  " + status);
        }
