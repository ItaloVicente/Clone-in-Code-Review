    mc.set("hello", 0, "world");
    String result = (String) mc.get("hello");
    assert (result.equals("world"));

    for (i = 0; i < 10000; i++) {
      String res = (String) mc.get("test" + i);
      assert (res.equals(i.toString()));
    }

    mc.shutdown(3, TimeUnit.SECONDS);
  }
