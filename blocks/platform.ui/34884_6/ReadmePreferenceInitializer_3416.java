package org.eclipse.ui.examples.readmetool;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ReadmePlugin extends AbstractUIPlugin {
    
    public static final String PLUGIN_ID = "org.eclipse.ui.examples.readmetool"; //$NON-NLS-1$
    
    private static ReadmePlugin inst;

    public ReadmePlugin() {
        if (inst == null)
            inst = this;
    }

    static public ReadmePlugin getDefault() {
        return inst;
    }

}
