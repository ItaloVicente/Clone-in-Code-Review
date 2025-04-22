          long waitTime = (reconnectAttempt++)*backoffTime;
          if(reconnectAttempt >= 10) {
            waitTime = maxWaitTime;
          }
          LOGGER.log(Level.INFO, "Reconnect attempt {0}, waiting {1}ms",
            new Object[]{reconnectAttempt, waitTime});
          Thread.sleep(waitTime);

