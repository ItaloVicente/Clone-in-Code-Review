  public static <T> int getDistinctResultCount(int num, Callable<T> callable)
    throws Throwable {
    IdentityHashMap<T, Object> found = new IdentityHashMap<T, Object>();
    Collection<SyncThread<T>> threads = getCompletedThreads(num, callable);
    for (SyncThread<T> s : threads) {
      found.put(s.getResult(), new Object());
    }
    return found.size();
  }
