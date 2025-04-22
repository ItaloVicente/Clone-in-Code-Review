    public static RedactionLevel getRedactionLevel() {
        return redactionLevel;
    }

    public static void setRedactionLevel(RedactionLevel redactionLevel) {
        if (defaultFactory == null) {
            throw new NullPointerException("redactionLevel");
        }

        CouchbaseLoggerFactory.redactionLevel = redactionLevel;
    }

