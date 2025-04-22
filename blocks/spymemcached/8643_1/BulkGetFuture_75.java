      }
    }
    for (Operation op : ops) {
      if (op.isCancelled()) {
        status = new OperationStatus(false, "Cancelled");
        throw new ExecutionException(new RuntimeException("Cancelled"));
      }
      if (op.hasErrored()) {
        status = new OperationStatus(false, op.getException().getMessage());
        throw new ExecutionException(op.getException());
      }
    }
    Map<String, T> m = new HashMap<String, T>();
    for (Map.Entry<String, Future<T>> me : rvMap.entrySet()) {
      m.put(me.getKey(), me.getValue().get());
