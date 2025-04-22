    long mechsDiff = System.nanoTime() - mechsStart;
    String msg = String.format("SASL List Mechanisms took %dms on %s",
      mechsDiff, node.toString());
    Level level = mechsDiff
      >= AUTH_ROUNDTRIP_THRESHOLD ? Level.WARN : Level.DEBUG;
    getLogger().log(level, msg);
