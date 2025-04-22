package org.eclipse.ui.tests.views.properties.tabbed;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator
    extends AbstractUIPlugin {

    private static Activator plugin;

    public Activator() {
        plugin = this;
    }

    @Override
	public void start(BundleContext context)
        throws Exception {
        super.start(context);
    }

    @Override
	public void stop(BundleContext context)
        throws Exception {
        super.stop(context);
        plugin = null;
    }

    public static Activator getDefault() {
        return plugin;
    }

    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
            "org.eclipse.ui.tests.views.properties.tabbed", path); //$NON-NLS-1$
    }
}
