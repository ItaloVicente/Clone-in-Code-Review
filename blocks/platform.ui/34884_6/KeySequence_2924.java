
package org.eclipse.ui.keys;

import org.eclipse.ui.internal.keys.CompactKeyFormatter;
import org.eclipse.ui.internal.keys.EmacsKeyFormatter;
import org.eclipse.ui.internal.keys.FormalKeyFormatter;

@Deprecated
public final class KeyFormatterFactory {
    private static final IKeyFormatter COMPACT_KEY_FORMATTER = new CompactKeyFormatter();

    private static final IKeyFormatter FORMAL_KEY_FORMATTER = new FormalKeyFormatter();

    private static final IKeyFormatter EMACS_KEY_FORMATTER = new EmacsKeyFormatter();

    private static IKeyFormatter defaultKeyFormatter = FORMAL_KEY_FORMATTER;

    public static final IKeyFormatter getCompactKeyFormatter() {
        return COMPACT_KEY_FORMATTER;
    }

    public static IKeyFormatter getDefault() {
        return defaultKeyFormatter;
    }

    public static IKeyFormatter getEmacsKeyFormatter() {
        return EMACS_KEY_FORMATTER;
    }

    public static IKeyFormatter getFormalKeyFormatter() {
        return FORMAL_KEY_FORMATTER;
    }

    public static void setDefault(IKeyFormatter defaultKeyFormatter) {
        if (defaultKeyFormatter == null) {
			throw new NullPointerException();
		}

        KeyFormatterFactory.defaultKeyFormatter = defaultKeyFormatter;
    }

    private KeyFormatterFactory() {
    }
}
