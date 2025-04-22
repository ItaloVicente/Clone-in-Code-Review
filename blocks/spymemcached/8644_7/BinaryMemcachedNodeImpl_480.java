      while (writeQ.peek() instanceof StoreOperation
          && og.size() < MAX_SET_OPTIMIZATION_COUNT
          && og.bytes() < MAX_SET_OPTIMIZATION_BYTES) {
        CASOperation o = (CASOperation) writeQ.remove();
        if (!o.isCancelled()) {
          og.addOperation(o);
        }
      }
