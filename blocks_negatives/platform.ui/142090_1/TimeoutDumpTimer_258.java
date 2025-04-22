    /**
     * SECONDS_BEFORE_TIMEOUT_BUFFER is the time we allow ourselves to take stack traces delay
     * "SECONDS_BETWEEN_DUMPS", then do it again. On current build machine, it takes about 30
     * seconds to do all that, so 2 minutes should be sufficient time allowed for most machines.
     * Though, should increase, say, if we increase the "time between dumps" to a minute or more.
     */
    private static final int SECONDS_BEFORE_TIMEOUT_BUFFER = 120;

    /**
     * SECONDS_BETWEEN_DUMPS is the time we wait from first to second dump of stack trace. In most
     * cases, this should suffice to determine if still busy doing something, or, hung, or waiting
     * for user input.
     */
    private static final int SECONDS_BETWEEN_DUMPS = 5;
