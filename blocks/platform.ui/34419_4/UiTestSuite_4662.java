package org.eclipse.ui.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.tests.decorators.BackgroundColorDecorator;
import org.eclipse.ui.tests.dynamicplugins.TestInstallUtil;
import org.eclipse.ui.tests.menus.MenuBuilder;
import org.osgi.framework.BundleContext;

public class TestPlugin extends AbstractUIPlugin implements IStartup {
    private static TestPlugin plugin;

    private ResourceBundle resourceBundle;

    private static boolean earlyStartupCalled = false;

    public static final String PLUGIN_ID = "org.eclipse.ui.tests";

    public TestPlugin() {
        plugin = this;
        try {
            resourceBundle = ResourceBundle
                    .getBundle("org.eclipse.ui.tests.TestPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    public static TestPlugin getDefault() {
        return plugin;
    }

    public static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    public static String getResourceString(String key) {
        ResourceBundle bundle = TestPlugin.getDefault().getResourceBundle();
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public ImageDescriptor getImageDescriptor(String relativePath) {
        String iconPath = "icons/";
        try {
            URL installURL = getDescriptor().getInstallURL();
            URL url = new URL(installURL, iconPath + relativePath);
            return ImageDescriptor.createFromURL(url);
        } catch (MalformedURLException e) {
            return ImageDescriptor.getMissingImageDescriptor();
        }
    }

    @Override
	public void earlyStartup() {
        earlyStartupCalled = true;
    }

    public static boolean getEarlyStartupCalled() {
        return earlyStartupCalled;
    }

    public static void clearEarlyStartup() {
        earlyStartupCalled = false;
    }

    @Override
	public void start(BundleContext context) throws Exception {
        TestInstallUtil.setContext(context);
        super.start(context);
        earlyStartup();
        MenuBuilder.addMenuContribution();
    }

    @Override
	public void stop(BundleContext context) throws Exception {
    	MenuBuilder.removeMenuContribution();
        TestInstallUtil.setContext(null);
        super.stop(context);
        BackgroundColorDecorator.color = null;
    }
}
