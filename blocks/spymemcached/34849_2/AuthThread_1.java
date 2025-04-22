      } finally {
        long stepDiff = System.nanoTime() - stepStart;
        msg = String.format("SASL Step took %dms on %s",
          stepDiff, node.toString());
        level = mechsDiff
          >= AUTH_ROUNDTRIP_THRESHOLD ? Level.WARN : Level.DEBUG;
        getLogger().log(level, msg);
