package org.eclipse.ui.examples.propertysheet;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PropertySheetPlugin extends AbstractUIPlugin {
    private static PropertySheetPlugin inst;

    public PropertySheetPlugin() {
        if (inst == null)
            inst = this;
    }

    static public PropertySheetPlugin getDefault() {
        return inst;
    }
}
