package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public final class UIPlugin extends AbstractUIPlugin {

    private static UIPlugin inst;

    public UIPlugin() {
        super();
        inst = this;
    }

    protected ImageRegistry createImageRegistry() {
        Assert.isLegal(false);
        return null;
    }

    public ImageRegistry getImageRegistry() {
        Assert.isLegal(false);
        return null;
    }

    public static UIPlugin getDefault() {
        return inst;
    }

 
    public void start(BundleContext context) throws Exception {
        super.start(context);

        PrefUtil.setUICallback(new PrefUtil.ICallback() {
            public IPreferenceStore getPreferenceStore() {
                return UIPlugin.this.getPreferenceStore();
            }

            public void savePreferences() {
                UIPlugin.this.savePluginPreferences();
            }
        });
    }
}
