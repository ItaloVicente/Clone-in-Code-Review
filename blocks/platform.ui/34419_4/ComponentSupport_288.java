package org.eclipse.ui.internal.editorsupport.win32;

import com.ibm.icu.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class OleMessages {
    private static final String RESOURCE_BUNDLE = "org.eclipse.ui.internal.editorsupport.win32.messages";//$NON-NLS-1$

    private static ResourceBundle bundle = ResourceBundle
            .getBundle(RESOURCE_BUNDLE);

    private OleMessages() {
    }

    public static String format(String key, Object[] args) {
        return MessageFormat.format(getString(key), args);
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public static String getString(String key, String def) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return def;
        }
    }
}
