      long saslRequestDiff = System.currentTimeMillis() - saslRequestStart;

      String msg = String.format("SASL Auth Step on node %s took %dms",
        node.toString(), saslRequestDiff);
      Level level =
        saslRequestDiff >= AUTH_TIME_THRESHOLD ? Level.WARN : Level.DEBUG;
      getLogger().log(level, msg);
