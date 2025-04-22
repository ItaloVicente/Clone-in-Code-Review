    byte[] data = new byte[2048];
    Random r = new Random();
    r.nextBytes(data);
    final int hashcode = Arrays.hashCode(data);
    final Collection<String> keys = new ArrayList<String>();
    for (int i = 0; i < 50; i++) {
      client.set("k" + i, 60, data);
      keys.add("k" + i);
    }
