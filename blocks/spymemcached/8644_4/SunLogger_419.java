    if (logRequestor != null) {
      if (e != null) {
        sunLogger.logp(sLevel, logRequestor.getClassName(),
            logRequestor.getMethodName(), message.toString(), e);
      } else {
        sunLogger.logp(sLevel, logRequestor.getClassName(),
            logRequestor.getMethodName(), message.toString());
      }
    } else {
      if (e != null) {
        sunLogger.log(sLevel, message.toString(), e);
      } else {
        sunLogger.log(sLevel, message.toString());
      }
    }
  }
