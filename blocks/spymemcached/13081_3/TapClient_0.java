    final TapStream ts = new TapStream();
    conn.broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        Operation op =  conn.getOpFactory().tapCustom(id, message,
            new TapOperation.Callback() {
            public void receivedStatus(OperationStatus status) {
            }
            public void gotData(ResponseMessage tapMessage) {
              rqueue.add(tapMessage);
              messagesRead++;
            }
            public void gotAck(MemcachedNode node, TapOpcode opcode,
                int opaque) {
              rqueue.add(new TapAck(conn, node, opcode, opaque, this));
            }
            public void complete() {
              latch.countDown();
            }
          });
        ts.addOp((TapOperation)op);
        return op;
      }
    });
