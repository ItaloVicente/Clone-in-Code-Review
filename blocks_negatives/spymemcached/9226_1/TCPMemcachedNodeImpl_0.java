      Operation o = getCurrentWriteOp();

      if (o != null && o.getState() == OperationState.WRITE_QUEUED) {
        if (o.isCancelled()) {
          getLogger().debug("Not writing cancelled op.");
          Operation cancelledOp = removeCurrentWriteOp();
          assert o == cancelledOp;
          return;
        } else if (o.isTimedOut(defaultOpTimeout)) {
          getLogger().debug("Not writing timed out op.");
          Operation timedOutOp = removeCurrentWriteOp();
          assert o == timedOutOp;
          return;
        } else {
          o.writing();
          if (!(o instanceof TapAckOperationImpl)) {
            readQ.add(o);
          }
        }
      }
