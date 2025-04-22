package org.eclipse.e4.ui.tests;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class Activator extends Plugin {

	private ServiceTracker eventAdminTracker;
	private ServiceTracker packageAdminTracker;
	public static final String PLUGIN_ID = "org.eclipse.e4.ui.tests";

	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		if (eventAdminTracker != null) {
			eventAdminTracker.close();
			eventAdminTracker = null;
		}
		if (packageAdminTracker != null) {
			packageAdminTracker.close();
			packageAdminTracker = null;
		}
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public EventAdmin getEventAdmin() {
		if (eventAdminTracker == null) {
			BundleContext bundleContext = plugin.getBundle().getBundleContext();
			if (bundleContext == null)
				return null;
			eventAdminTracker = new ServiceTracker(bundleContext,
					EventAdmin.class.getName(), null);
			eventAdminTracker.open();
		}
		return (EventAdmin) eventAdminTracker.getService();
	}

	public PackageAdmin getPackageAdmin() {
		if (packageAdminTracker == null) {
			BundleContext bundleContext = plugin.getBundle().getBundleContext();
			if (bundleContext == null)
				return null;
			packageAdminTracker = new ServiceTracker(bundleContext,
					PackageAdmin.class.getName(), null);
			packageAdminTracker.open();
		}
		return (PackageAdmin) packageAdminTracker.getService();
	}

	public static String asURI(Class<?> clazz) {
		PackageAdmin pkgadm = getDefault().getPackageAdmin();
		return "bundleclass://" + pkgadm.getBundle(clazz).getSymbolicName()
				+ '/' + clazz.getName();
	}

}
