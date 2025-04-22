    ReducedOperationImpl op = new ReducedOperationImpl(null, ViewType.MAPREDUCE,
      new ViewCallback() {
        @Override
        public void receivedStatus(OperationStatus status) {
          assert status.isSuccess();
        }
