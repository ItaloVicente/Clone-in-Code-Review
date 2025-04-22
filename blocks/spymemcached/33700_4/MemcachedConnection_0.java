      if (op instanceof MultiGetOperationImpl) {
        for (String key : ((MultiGetOperationImpl) op).getRetryKeys()) {
          addOperation(key, opFact.get(key,
            (GetOperation.Callback) op.getCallback()));
        }
      } else if (op instanceof KeyedOperation) {
