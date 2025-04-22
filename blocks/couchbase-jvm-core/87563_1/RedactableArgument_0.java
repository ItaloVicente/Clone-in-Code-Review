        final RedactionLevel redactionLevel = CouchbaseLoggerFactory.getRedactionLevel();

        final boolean redact;
        switch (redactionLevel) {
            case NONE:
                redact = false;
                break;
            case PARTIAL:
                redact = (type == ArgumentType.USER);
                break;
            case FULL:
                redact = true;
                break;
            default:
                throw new AssertionError("Unexpected redaction level: " + redactionLevel);
        }

        return redact ? "<" + type.tagName + ">" + message() + "</" + type.tagName + ">" : message();
