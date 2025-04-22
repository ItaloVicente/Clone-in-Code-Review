    /**
     * Helper method to perform potential log redaction.
     *
     * If this method returns true, it did perform the redaction and logging. If
     * it returns false no side effect (no log line) has been sent to the logging
     * implementation.
     *
     * @param format the log string
     * @param arguments the log arguments
     * @return true if redacted, false otherwise.
     */
    protected boolean infoRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            return false;
        }

        Object[] redacted = new Object[arguments.length];
        if (redactArgs(arguments, redacted)) {
            info(format, redacted);
            debug(format, arguments);
            return true;
        }

        return false;
    }

    /**
     * Helper method to perform potential log redaction.
     *
     * If this method returns true, it did perform the redaction and logging. If
     * it returns false no side effect (no log line) has been sent to the logging
     * implementation.
     *
     * @param format the log string
     * @param arguments the log arguments
     * @return true if redacted, false otherwise.
     */
    protected boolean warnRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            return false;
        }

        Object[] redacted = new Object[arguments.length];
        if (redactArgs(arguments, redacted)) {
            warn(format, redacted);
            debug(format, arguments);
            return true;
        }

        return false;
    }

    /**
     * Helper method to perform potential log redaction.
     *
     * If this method returns true, it did perform the redaction and logging. If
     * it returns false no side effect (no log line) has been sent to the logging
     * implementation.
     *
     * @param format the log string
     * @param arguments the log arguments
     * @return true if redacted, false otherwise.
     */
    protected boolean errorRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            return false;
        }

        Object[] redacted = new Object[arguments.length];
        if (redactArgs(arguments, redacted)) {
            error(format, redacted);
            debug(format, arguments);
            return true;
        }

        return false;
    }

    /**
     * Redact arguments for logging based on the policy level configured.
     *
     * @param arguments the input arguments.
     * @param redacted the redacted arguments injected, should be empty.
     * @return true if redacted at least one, false otherwise.
     */
    private boolean redactArgs(Object[] arguments, Object[] redacted) {
        boolean atLeastOneRedacted = false;
        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];
            if (arg instanceof RedactableArgument) {
                arguments[i] = arg.toString();

                if (redactionLevel == RedactionLevel.FULL ||
                        (redactionLevel == RedactionLevel.PARTIAL
                                && ((RedactableArgument) arg).type() == RedactableArgument.ArgumentType.USER)
                        ) {
                    redacted[i] = "-REDACTED-";
                    atLeastOneRedacted = true;
                } else {
                    redacted[i] = arg.toString();
                }
            }
        }
        return atLeastOneRedacted;
    }



