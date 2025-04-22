                                       long lastResponseReceived = e.lastResponse();
                                       long actualIdleTime;
                                       if (lastResponseReceived != 0) {
                                           actualIdleTime = System.nanoTime() - lastResponseReceived;
                                       } else {
                                           long lastConnected = e.lastConnectedAt();
                                           if (lastConnected != 0) {
                                               actualIdleTime = System.nanoTime() - lastConnected;
                                           } else {
                                               continue;
                                           }
                                       }

                                       long diffs = TimeUnit.NANOSECONDS.toSeconds(actualIdleTime);
