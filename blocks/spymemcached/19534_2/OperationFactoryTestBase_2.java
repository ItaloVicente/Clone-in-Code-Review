      public void gotData(String key, long cas) {
      }

      public void receivedStatus(OperationStatus status) {
        fail("Unexpected status:  " + status);
      }
    };
