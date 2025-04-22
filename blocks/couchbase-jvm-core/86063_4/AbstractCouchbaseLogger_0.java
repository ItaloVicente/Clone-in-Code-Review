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



