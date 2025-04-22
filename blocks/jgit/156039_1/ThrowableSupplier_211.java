
package org.eclipse.jgit.niofs.internal.util;

import java.util.Collection;
import java.util.Map;

public class Preconditions {

    protected Preconditions() {
        throw new IllegalStateException("This class should be not instantiated!");
    }

    public static void checkCondition(final String name
                                      final boolean condition) {
        if (!condition) {
            throw new IllegalStateException("Condition '" + name + "' is invalid!");
        }
    }

    public static <T> void checkEachParameterNotNull(final String name
                                                     final T... parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
        }
        for (final Object parameter : parameters) {
            if (parameter == null) {
                throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
            }
        }
    }

    public static <T extends Collection<?>> T checkNotEmpty(final String name
                                                            final T parameter) {
        if (parameter == null || parameter.size() == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
        return parameter;
    }

    public static void checkNotEmpty(final String name
                                     final Map<?
        if (parameter == null || parameter.size() == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
    }

    public static String checkNotEmpty(final String name
                                       final String parameter) {
        if (parameter == null || parameter.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
        return parameter;
    }

    public static <T> T[] checkNotEmpty(final String name
                                        final T[] parameter) {
        if (parameter == null || parameter.length == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
        return parameter;
    }

    public static <T> T checkNotNull(final String name
                                     final T parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
        }
        return parameter;
    }

    public static void checkNullMandatory(final String name
                                          final Object parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be null!");
        }
    }

    public static <T> T checkInstanceOf(final String name
                                        Object parameter
                                        final Class<T> clazz) {
        checkNotNull(name
                     parameter);
        checkNotNull("clazz"
                     clazz);
        if (!clazz.isInstance(parameter)) {
            throw new IllegalArgumentException("Parameter named '" + name + "' is not instance of clazz '" + clazz.getName() + "'!");
        }

        return clazz.cast(parameter);
    }
}
