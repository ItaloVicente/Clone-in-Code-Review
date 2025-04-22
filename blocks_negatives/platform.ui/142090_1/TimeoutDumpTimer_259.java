        try {
            /*
             * The delay (in ms) is the sum of - the expected time it took for launching the current
             * VM and reaching this method - the time it will take to run the garbage collection and
             * dump all the infos (twice)
             */
            int delay = SECONDS_BEFORE_TIMEOUT_BUFFER * 1000;

            int timeout = Integer.parseInt(timeoutArg) - delay;
            String time0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US).format(new Date());
            logInfo("starting DumpStackTracesTimer with timeout=" + timeout + " at " + time0);
            if (timeout > 0) {
                new Timer("DumpStackTracesTimer", true).schedule(new TimeoutDumpTimer(timeoutArg, outputDirectory), timeout);
            } else {
                logWarning("DumpStackTracesTimer argument error: '-timeout " + timeoutArg
                        + "' was too short to accommodate time delay required (" + delay + ").");
            }
        } catch (NumberFormatException e) {
            logError("Error parsing timeout argument: " + timeoutArg, e);
        }
    }

    @Override
    public void run() {
        dump(0);
        try {
            Thread.sleep(SECONDS_BETWEEN_DUMPS * 1000);
        } catch (InterruptedException e) {
        }
        dump(SECONDS_BETWEEN_DUMPS);
    }
