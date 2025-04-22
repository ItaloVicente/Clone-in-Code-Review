              + ":8091/pools")), "abucket", "abucket", "apassword");

      try {
        client.tapBackfill(null, 5, TimeUnit.SECONDS);
      } catch (RuntimeException e) {
        System.err.println(e.getMessage());
        return;
      }
