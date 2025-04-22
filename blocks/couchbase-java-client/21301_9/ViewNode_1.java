      waittime = TimeUnit.MILLISECONDS.convert(time, unit);
    }

    connMgr.shutdown(waittime);
    try {
      ioThread.join(waittime);
    } catch (InterruptedException ex) {
      getLogger().error("Interrupt " + ex + " received while waiting for node "
        + addr.getHostName() + " to shut down.");
