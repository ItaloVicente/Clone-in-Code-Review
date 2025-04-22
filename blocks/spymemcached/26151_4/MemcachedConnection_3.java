            if (!done || testOp.isCancelled() || testOp.hasErrored()
              || testOp.isTimedOut()) {
              throw new ConnectException("Could not send noop upon connect! "
                + "This may indicate a running, but not responding memcached "
                + "instance.");
            }
