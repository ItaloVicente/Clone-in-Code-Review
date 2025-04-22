    final List<GenericCompletionListener<? extends Future<T>>> copy =
      new ArrayList<GenericCompletionListener<? extends Future<T>>>();
    synchronized(this) {
      copy.addAll(listeners);
      listeners = new ArrayList<GenericCompletionListener<? extends Future<T>>>();
    }
