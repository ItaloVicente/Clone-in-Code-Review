              @Override
              public void complete() {
                latch.countDown();
              }
            });

            testOp.setHandlingNode(node);
            testOp.initialize();

            checkState();
            insertOperation(node, testOp);
            node.copyInputQueue();

            boolean done = false;
            if(sk.isValid()) {
              long timeout = TimeUnit.MILLISECONDS.toNanos(
                connectionFactory.getOperationTimeout());
              for(long stop = System.nanoTime() + timeout;
                stop > System.nanoTime();) {
                handleWrites(sk, node);
                handleReads(sk, node);
                if(done = (latch.getCount() == 0)) {
                  break;
                }
