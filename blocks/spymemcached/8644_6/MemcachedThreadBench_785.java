      }
      long end = System.currentTimeMillis();

      stat.setterTime = end - begin;
    }

    private synchronized void flush() {
      if (TOTAL.intValue() >= MAX_QUEUE) {
        mc.waitForQueues(5, TimeUnit.SECONDS);
        TOTAL.set(0);
      }
    }
  }

  private static class GetterThread extends Thread {
    private final MemcachedClient mc;
    private final WorkerStat stat;

    GetterThread(MemcachedClient c, WorkerStat st) {
      stat = st;
      mc = c;
    }

    @Override
    public void run() {
      String keyBase = "testKey";

      long begin = System.currentTimeMillis();
      for (int i = stat.start; i < stat.start + stat.runs; i++) {
        String str = (String) mc.get("" + i + keyBase);
        assert str != null;
      }
      long end = System.currentTimeMillis();

      stat.getterTime = end - begin;
    }
  }
