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
