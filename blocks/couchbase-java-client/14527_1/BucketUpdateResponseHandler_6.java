    Throwable ex = e.getCause();
    LOGGER.log(Level.WARNING, "Exception occurred: " + ex.getMessage() + "\n");
    StringBuilder sb = new StringBuilder();
    for (StackTraceElement one : ex.getStackTrace()) {
      sb.append(one.toString());
      sb.append("\n");
    }
    LOGGER.log(Level.WARNING, sb.toString());
