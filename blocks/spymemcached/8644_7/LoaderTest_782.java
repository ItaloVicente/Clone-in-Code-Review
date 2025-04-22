    Future<Boolean> f = null;
    for (int i = 0; i < count; i++) {
      f = cl.push("k" + i, "some value");
    }
    if (f != null) {
      try {
        f.get(1, TimeUnit.MINUTES);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
