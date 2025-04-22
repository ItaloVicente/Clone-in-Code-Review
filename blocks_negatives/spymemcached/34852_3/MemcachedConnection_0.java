      if (op instanceof MultiGetOperationImpl) {
        for (String key : ((MultiGetOperationImpl) op).getRetryKeys()) {
          addOperation(key, opFact.get(key,
            (GetOperation.Callback) op.getCallback()));
        }
      } else if (op instanceof KeyedOperation) {
        KeyedOperation ko = (KeyedOperation) op;
        int added = 0;
        for (Operation newop : opFact.clone(ko)) {
          if (newop instanceof KeyedOperation) {
            KeyedOperation newKeyedOp = (KeyedOperation) newop;
            for (String k : newKeyedOp.getKeys()) {
              addOperation(k, newop);
            }
          } else {
            newop.cancel();
            getLogger().warn("Could not redistribute cloned non-keyed " +
              "operation", newop);
