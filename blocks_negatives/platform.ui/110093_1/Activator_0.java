public class Activator extends Plugin {

	private ServiceTracker packageAdminTracker;
	public static final String PLUGIN_ID = "org.eclipse.e4.ui.tests";

	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (packageAdminTracker != null) {
			packageAdminTracker.close();
			packageAdminTracker = null;
		}
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
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
