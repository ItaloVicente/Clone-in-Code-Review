    Thread.sleep(50);
    for (Future<Object> f : c) {
      try {
          f.get(1, TimeUnit.SECONDS);
      } catch (TimeoutException e) {
      } catch (ExecutionException e) {
