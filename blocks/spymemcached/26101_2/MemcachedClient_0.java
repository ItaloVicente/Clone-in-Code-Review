  private String buildTimeoutMessage(long timeWaited, TimeUnit unit) {
    StringBuilder message = new StringBuilder();

    message.append(MessageFormat.format("waited {0} ms.",
      unit.convert(timeWaited, TimeUnit.MILLISECONDS)));
    message.append(" Node status: ").append(mconn.connectionsStatus());
    return message.toString();
  }

