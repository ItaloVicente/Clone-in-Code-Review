    HttpOperationImpl op = new TestOperationPutImpl(request,
      new TestCallback() {
        private String json;

        @Override
        public void receivedStatus(OperationStatus status) {
          crv.set(json, status);
        }

        @Override
        public void complete() {
          couchLatch.countDown();
        }

        @Override
        public void getData(String response) {
          json = response;
        }
      });
