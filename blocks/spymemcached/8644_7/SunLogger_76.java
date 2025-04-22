    Throwable t = new Throwable();
    StackTraceElement[] ste = t.getStackTrace();
    StackTraceElement logRequestor = null;
    String alclass = AbstractLogger.class.getName();
    for (int i = 0; i < ste.length && logRequestor == null; i++) {
      if (ste[i].getClassName().equals(alclass)) {
        if (i + 1 < ste.length) {
          logRequestor = ste[i + 1];
          if (logRequestor.getClassName().equals(alclass)) {
            logRequestor = null;
          } // Also AbstractLogger
        } // Found something that wasn't abstract logger
      } // check for abstract logger
    }
