    DocsOperationImpl op = new DocsOperationImpl(null, ViewType.MAPREDUCE,
      new ViewCallback() {
        @Override
        public void receivedStatus(OperationStatus status) {
          assert status.isSuccess();
        }
