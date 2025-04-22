    final CountDownLatch latch = new CountDownLatch(1);
    final Operation op = conn.getOpFactory().tapDump(id,
        new TapOperation.Callback() {
        public void receivedStatus(OperationStatus status) {
        }
        public void gotData(ResponseMessage tapMessage) {
          rqueue.add(tapMessage);
          messagesRead++;
        }
        public void gotAck(TapOpcode opcode, int opaque) {
          rqueue.add(new TapAck(conn, opcode, opaque, this));
        }
        public void complete() {
          latch.countDown();
        }
      });
