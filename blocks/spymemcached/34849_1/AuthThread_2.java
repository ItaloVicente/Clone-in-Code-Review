
    long totalDiff = System.nanoTime() - totalStart;
    msg = String.format("SASL Auth took %dms on %s",
      totalDiff, node.toString());
    level = mechsDiff >= AUTH_TOTAL_THRESHOLD ? Level.WARN : Level.DEBUG;
    getLogger().log(level, msg);
