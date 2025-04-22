    Handler consoleHandler = null;
    for (Handler handler : topLogger.getHandlers()) {
      if (handler instanceof ConsoleHandler) {
        consoleHandler = handler;
        break;
      }
