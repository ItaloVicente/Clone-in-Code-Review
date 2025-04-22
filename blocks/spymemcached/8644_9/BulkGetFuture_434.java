    Map<String, T> m = new HashMap<String, T>();
    for (Map.Entry<String, Future<T>> me : rvMap.entrySet()) {
      m.put(me.getKey(), me.getValue().get());
    }
    return m;
  }

  public OperationStatus getStatus() {
    if (status == null) {
      try {
        get();
      } catch (InterruptedException e) {
        status = new OperationStatus(false, "Interrupted");
        Thread.currentThread().interrupt();
      } catch (ExecutionException e) {
        return status;
      }
    }
    return status;
  }

  public void setStatus(OperationStatus s) {
    status = s;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public boolean isDone() {
    return latch.getCount() == 0;
  }
