
package com.couchbase.client.core.logging;

public class RedactableArgument {

    private final ArgumentType type;

    private final String message;

    private RedactableArgument(final ArgumentType type, final String message) {
        this.type = type;
        this.message = message;
    }

    public static RedactableArgument user(final String message) {
        return new RedactableArgument(ArgumentType.USER, message);
    }

    public static RedactableArgument meta(final String message) {
        return new RedactableArgument(ArgumentType.META, message);
    }

    public static RedactableArgument system(final String message) {
        return new RedactableArgument(ArgumentType.SYSTEM, message);
    }

    public ArgumentType type() {
        return type;
    }

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    public enum ArgumentType {
        USER,
        META,
        SYSTEM
    }

}
