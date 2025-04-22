package org.eclipse.ui.examples.propertysheet;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


    private static final String RESOURCE_BUNDLE = "org.eclipse.ui.examples.propertysheet.messages";//$NON-NLS-1$

    private static ResourceBundle fgResourceBundle = ResourceBundle
            .getBundle(RESOURCE_BUNDLE);

    private MessageUtil() {
    }

    public static String format(String key, Object[] args) {
        return MessageFormat.format(getString(key), args);
    }

    public static String getString(String key) {
        try {
            return fgResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$
        }
    }
}
