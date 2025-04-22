    @Override
    public void logRedacted(final CouchbaseLogLevel level, final String format, final Object... arguments) {
        switch (level) {
            case TRACE:
                throw new IllegalArgumentException("Only INFO, WARN, and ERROR can be redacted!");
            case DEBUG:
                throw new IllegalArgumentException("Only INFO, WARN, and ERROR can be redacted!");
            case INFO:
                infoRedacted(format, arguments);
                break;
            case WARN:
                warnRedacted(format, arguments);
                break;
            case ERROR:
                errorRedacted(format, arguments);
                break;
            default:
                throw new Error();
        }
    }

    @Override
    public void infoRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            info(format, arguments);
            return;
        }

        Object[] copied = Arrays.copyOf(arguments, arguments.length);
        boolean redacted = redactArguments(arguments);

        info(format, arguments);

        if (redacted) {
            debug(format, copied);
        }
    }

    @Override
    public void warnRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            warn(format, arguments);
            return;
        }

        Object[] copied = Arrays.copyOf(arguments, arguments.length);
        boolean redacted = redactArguments(arguments);

        warn(format, arguments);

        if (redacted) {
            debug(format, copied);
        }
    }

    @Override
    public void errorRedacted(final String format, final Object... arguments) {
        if (redactionLevel == RedactionLevel.NONE) {
            error(format, arguments);
            return;
        }

        Object[] copied = Arrays.copyOf(arguments, arguments.length);
        boolean redacted = redactArguments(arguments);

        error(format, arguments);

        if (redacted) {
            debug(format, copied);
        }
    }

    private boolean redactArguments(Object[] input) {
        if (redactionLevel == RedactionLevel.NONE) {
            return false;
        }

        boolean redacted = false;
        for (int i = 0; i < input.length; i++) {
            Object elem = input[i];
            if (elem instanceof RedactableArgument) {
                RedactableArgument.ArgumentType type = ((RedactableArgument) elem).type();
                if (redactionLevel == RedactionLevel.FULL
                        || (redactionLevel == RedactionLevel.PARTIAL && type == RedactableArgument.ArgumentType.USER))
                input[i] = "- REDACTED -";
                redacted = true;
            }
        }
        return redacted;
    }

